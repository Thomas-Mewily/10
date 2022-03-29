public class Useful 
{
    // Thank to https://stackoverflow.com/questions/4519557/is-there-a-way-to-throw-an-exception-without-adding-the-throws-declaration/4519576
    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void crash(Throwable exception, Object dummy) throws T
    {
        throw (T) exception;
    }

    public static void crash(String errorMessage)
    {
        Useful.<RuntimeException>crash(new Exception(errorMessage), null);
    }

    public static void assertFalse(boolean crashIfFalse, String errorMessage) { assertTrue(!crashIfFalse, errorMessage); }
    public static void assertTrue(boolean crashIfTrue, String errorMessage)
    { 
        if(crashIfTrue){ crash(errorMessage); }
    }
}