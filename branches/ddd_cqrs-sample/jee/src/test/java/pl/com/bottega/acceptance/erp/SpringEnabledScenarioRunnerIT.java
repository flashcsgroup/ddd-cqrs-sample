package pl.com.bottega.acceptance.erp;

import org.jbehave.core.reporters.StoryReporterBuilder;

public class SpringEnabledScenarioRunnerIT {

    public static class MyReportBuilder extends StoryReporterBuilder {
        public MyReportBuilder() {
            withFailureTrace(true).withFormats(org.jbehave.core.reporters.Format.CONSOLE);
        }
    }
}