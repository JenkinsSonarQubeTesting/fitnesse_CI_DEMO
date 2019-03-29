package fitnesseMain;

import java.util.*; //BAD: star imports

class  Hello
{
  public final static String hello = "helloWorld!"; //BAD: modifier order

  public String badCommaWhiteSpaceBeforeAndAfter(String input ,String anotherInput)
    {
        return "badCommaWhiteSpaceBeforeAndAfter";
    }

    public void badGenericsWhiteSpace()
    {
      List< String> strings = Arrays.asList("foo", "bar");
      System.out.print(strings.get(0));
    }

    public boolean badWhiteSpaces()
    {
        int sum = 2+2;
        int diff = 2-1;
        System.out. println("hello");
        return sum+diff==5;
    }

    @SafeVarargs public final void badAnnotations(String... badVarargsAnnotation)
    {
        @SuppressWarnings("default") Hello badSuppressWarningsAnnotation;
    }
    
    public void badArrayTypeStyle()
    {
      String args[] = {"hello", "world"};
    }
    
    public void badBraces(){
      System.out.println("bad");
      System.out.println("braces!");}
      
    public void badMultipleStringLiterals()
    {
      //TODO
      System.out.println("bad multiple string literals");
    }
        
    public void badMultipleEmptyLines()
    {
    
    
      System.out.println("bad");
    }
    
    
}
