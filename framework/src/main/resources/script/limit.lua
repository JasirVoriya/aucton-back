-- 获取方法签名特征
local key = KEYS[1]
-- 限流的阈值
local limit = tonumber(ARGV[1])
-- 限流的时间
local second = tonumber(ARGV[2])
-- 当前流量大小
local count = tonumber(redis.call('get', key) or '0')

if count == 0 then
    redis.call('setex', key, second, 1)
    return true
end
-- 是否超出限流标准
if count >= limit then
    return false
end
-- 执行计算器自加
count = tonumber(redis.call('incr',key))
return true
