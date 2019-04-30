package Project.Settings;

import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;

import java.util.ArrayList;

import java.util.List;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public enum TestSuites {
    EriBankInstrumented("Project.Tests.EriBank_Tests.Instrumented_Eribank_Tests"),
    WebTests("Project.Tests.WebTests"),
    //AllTest("Project.Tests"),
    AllTest("Project.Tests", "Project.Selenium.SeleniumTests"),
//    SeleniumTests(Project.Selenium.SeleniumTests.PerformanceTest.class),
    SeleniumTests("Project.Selenium.SeleniumTests"),
    //OneTimeTest(Project.Test_NotInLongRun.RegressionTests_noLongRun.GridTest.class),
    //OneTimeTest_1("Project.Test_NotInLongRun.InstallAndLaunch_Tests.UnlockManyTimes"),
   // LongRunTest_NoRelease(LongRunTestWithoutReleaseOriginal.class),
   // BaseTest_LongRun(Project.LongRun.BaseTest_LongRun.class),
    NothingTest(Project.Tests.NothingTest.class), /*Check the code environment without actual test*/
    Maintenance("Project.Maintenance"),


    //Examples
    Packgess("Package1", "Package1"),
    //Packgess_Class(Arrays.asList("Package1","Package1"), Chrome.class, Chrome.class),
    ;


    public List<DiscoverySelector> selectors;

    //For classes list only
    TestSuites(Class... classes) {

        selectors = new ArrayList<DiscoverySelector>();
        for (Class c: classes) {
            selectors.add(selectClass(c));
        }
    }


    //For packages only
    TestSuites(String... Packages) {
        selectors = new ArrayList<DiscoverySelector>();
        for (String  p: Packages) {
            selectors.add(DiscoverySelectors.selectPackage(p));
        }
    }

    //For packages And classes
    TestSuites(List<String> Packages, Class... classes) {
        selectors = new ArrayList<DiscoverySelector>();
        for (String  p: Packages) {
            selectors.add(DiscoverySelectors.selectPackage(p));
        }
        for (Class c: classes) {
            selectors.add(selectClass(c));
        }
    }


    public String toString(){
        return this.name();
    }

}


