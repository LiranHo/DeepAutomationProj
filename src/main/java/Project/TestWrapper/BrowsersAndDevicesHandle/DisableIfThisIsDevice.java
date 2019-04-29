package Project.TestWrapper.BrowsersAndDevicesHandle;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import static sun.audio.AudioDevice.device;

public class DisableIfThisIsDevice implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        String type = Thread.currentThread().getName();
        if(type.contains("Browser")) {
            return ConditionEvaluationResult.enabled("enabled this is a Browser");
        } else {
            return ConditionEvaluationResult.disabled("disabled this is a Device");
        }
    }


}



