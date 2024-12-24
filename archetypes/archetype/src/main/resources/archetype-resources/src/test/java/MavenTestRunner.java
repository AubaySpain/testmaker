package ${package};

import org.testng.annotations.Test;
import ${package}.runner.TestRunner;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;

public class MavenTestRunner extends InputParamsBasic {

    @Test
    public void executeTestRunner() throws Exception {
    	var args = mapSystemParametersToArgs();
        TestRunner.main(args);
    }

}
