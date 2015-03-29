package ru.messageBoard.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestMessageBoard.class, TestValidator.class,
		TestFileFilter.class, TestDateFormat.class, TestReadTopic.class })
public class AllDataTests {

}
