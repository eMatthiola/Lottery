package cn.it.lottery.domain.support.ids.policy;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.it.lottery.domain.support.ids.IIdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @ClassName SnowFlake
 * @Description 雪花算法，用于生成单号
 * @Author Matthiola
 * @Date 2023/9/20 7:54
 */
@Component
public class SnowFlake implements IIdGenerator {



  private Snowflake snowflake;

  /*In summary, the code initializes a Snowflake ID generator by generating a unique worker ID based on the local host's
   IP address or its hash code and assigning a data center ID of 1.*/

    @PostConstruct
    public void init() {
        // 0 ~ 31 位，可以采用配置的方式使用
        long workId;

        try {
               /*The  try  block attempts to assign the value of  workerId  by converting the IPv4 address of the local host
        to a long value using the  NetUtil.ipv4ToLong()  method. If an exception occurs, it means the conversion failed,
         so the  catch  block is executed.*/
            workId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        }catch (Exception e) {
            /*In the  catch  block, the  workerId  is assigned the hash code of the local host's IP address converted to
             a string using the  NetUtil.getLocalhostStr()  method.*/
            workId = NetUtil.getLocalhostStr().hashCode();
        }
         /*The  workerId  is then right-shifted by 16 bits (  workerId >> 16  ) and bitwise ANDed with 31 (  & 31  ).
        This operation ensures that the value of  workerId  is within the range of 0 to 31*/
        workId = workId >> 16 & 31;

        long dataCenterId = 1L;
        snowflake= IdUtil.createSnowflake(workId,dataCenterId);

    }

    @Override
    public long nextId() {
       return snowflake.nextId();
    }
}
