package pl.com.bottega.erp.crm.acceptance;

import java.text.SimpleDateFormat;

import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingPaths;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.spring.SpringAnnotatedPathRunner;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.junit.runner.RunWith;

import pl.com.bottega.erp.crm.acceptance.SpringEnabledScenarioRunnerIT.MyDateConverter;
import pl.com.bottega.erp.crm.acceptance.SpringEnabledScenarioRunnerIT.MyReportBuilder;
import pl.com.bottega.erp.crm.acceptance.SpringEnabledScenarioRunnerIT.MyStoryLoader;

@RunWith(SpringAnnotatedPathRunner.class)
@UsingPaths(searchIn = "src/test/resources")
@Configure(storyLoader = MyStoryLoader.class, storyReporterBuilder = MyReportBuilder.class, parameterConverters = { MyDateConverter.class })
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true, ignoreFailureInView = true, storyTimeoutInSecs = 100, threads = 1, metaFilters = "-skip")
@UsingSpring(resources = "classpath:/stepsConfiguration.xml")
public class SpringEnabledScenarioRunnerIT {

    public static class MyReportBuilder extends StoryReporterBuilder {
        public MyReportBuilder() {
            this.withFormats(org.jbehave.core.reporters.Format.CONSOLE, org.jbehave.core.reporters.Format.TXT,
                    org.jbehave.core.reporters.Format.HTML, org.jbehave.core.reporters.Format.XML).withDefaultFormats();
        }
    }

    public static class MyStoryLoader extends LoadFromClasspath {
        public MyStoryLoader() {
            super(SpringEnabledScenarioRunnerIT.class.getClassLoader());
        }
    }

    public static class MyRegexPrefixCapturingPatternParser extends RegexPrefixCapturingPatternParser {
        public MyRegexPrefixCapturingPatternParser() {
            super("%");
        }
    }

    public static class MyDateConverter extends DateConverter {
        public MyDateConverter() {
            super(new SimpleDateFormat("yyyy-MM-dd"));
        }
    }
}