package redisTest;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        Set sentinels = new HashSet();
        sentinels.add(new HostAndPort("192.168.66.230", 26379).toString());
        sentinels.add(new HostAndPort("192.168.66.230", 26379).toString());
        sentinels.add(new HostAndPort("192.168.66.230", 26379).toString());
        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);
        System.out.println("Current master: " + sentinelPool.getCurrentHostMaster().toString());

        Jedis master = sentinelPool.getResource();
        master.set("test","hellow world");
        master.close();

        Jedis master2 = sentinelPool.getResource();
        String value = master2.get("test");
        System.out.println("test: " + value);
        master2.del("test");
        master2.close();
        sentinelPool.destroy();
    }
}
