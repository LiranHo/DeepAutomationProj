package Project.Tests.PerformanceTesting;

import Project.BaseTest;
import Project.Main;
import Project.PerformanceTesting.connectToDB;
import org.junit.jupiter.api.BeforeEach;

import java.net.MalformedURLException;
import java.util.Date;

public class PerformanceTesting_BaseTest extends BaseTest {

    @BeforeEach
    public void setUp_extension() throws MalformedURLException {
    }

    public long calculate_commnand_time(long start, long end){
        long time = end - start;
        int id = (int)new Date().getTime();
        connectToDB.insert((id<0 ? id*(-1) : id), "click", device.getSerialnumber(), time, Main.Cloud_version_number, Main.Cloud_build_number, Main.Cloud_version_number+"."+Main.Cloud_build_number);

        return time;

    }



}
