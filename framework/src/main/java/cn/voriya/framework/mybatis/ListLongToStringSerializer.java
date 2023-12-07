package cn.voriya.framework.mybatis;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ListLongToStringSerializer extends JsonSerializer<List<Long>> {

    @Override
    public void serialize(List<Long> longList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        List<String> stringList = longList.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        jsonGenerator.writeObject(stringList);
    }
}
