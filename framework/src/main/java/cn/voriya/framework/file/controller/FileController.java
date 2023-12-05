package cn.voriya.framework.file.controller;


import cn.voriya.framework.entity.enums.ResultCode;
import cn.voriya.framework.entity.vo.ResultMessage;
import cn.voriya.framework.exception.ServiceException;
import cn.voriya.framework.file.entity.dos.File;
import cn.voriya.framework.security.annotations.Login;
import cn.voriya.framework.security.enums.UserEnums;
import cn.voriya.framework.file.service.IFileService;
import cn.voriya.framework.utils.ResultUtil;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JasirVoriya
 * @since 2023-12-03
 */
@RestController
@RequestMapping("file")
public class FileController {
    private final IFileService fileService;

    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload")
    @Login(role = {UserEnums.USER, UserEnums.MANAGER})
    public ResultMessage<String> uploadFile(@RequestParam("file") MultipartFile file) {
        final Long id = fileService.uploadFile(file);
        return ResultUtil.data("" + id);
    }

    @SneakyThrows
    @GetMapping("download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        final File file = fileService.getById(id);
        if (file == null) {
            throw new ServiceException(ResultCode.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getType().getType()));  // 设置Content-Type
        return new ResponseEntity<>(file.getFile(), headers, HttpStatus.OK);
    }
}
