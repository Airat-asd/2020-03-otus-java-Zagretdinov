package testClass;

import testClass.annotations.After;
import testClass.annotations.Before;
import testClass.annotations.Test;
import testee.MessageBuilder;
import testee.MessageTemplateProvider;


public class MessageBuilderImplTest {

    public static final String TEMPLATE_NAME = "TemplateName";
    public static final String TEMPLATE_TEXT = "%s With best regards, %s";
    public static final String MESSAGE_TEXT = "How you doing?";
    public static final String SIGN = "Vasili";
    private MessageBuilder messageBuilder;
    private MessageTemplateProvider provider;

    @Before
    public void setUp() {
        System.out.println("Run method SetUp");
//        provider = Mockito.mock(MessageTemplateProvider.class);
//        messageBuilder = new MessageBuilderImpl(provider);
    }

    @Test
    public void buildMessage() {
        System.out.println("Run method buildMessage");

//                Mockito.when(provider.getMessageTemplate(TEMPLATE_NAME)).thenReturn(TEMPLATE_TEXT);
        String actualMessage = messageBuilder.buildMessage(TEMPLATE_NAME,
                MESSAGE_TEXT, SIGN);
//                Assertions.assertEquals(String.format(TEMPLATE_TEXT, MESSAGE_TEXT, SIGN),
//                        actualMessage);
    }

    @After
    public void setDown() {
        System.out.println("Run method setDown");
    }
}