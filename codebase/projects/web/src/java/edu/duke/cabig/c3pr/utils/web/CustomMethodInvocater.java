package edu.duke.cabig.c3pr.utils.web;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * This class represents a Method, the list of arguments to be passed to that method, and the object
 * on which the method is to be invoked. The invoke() method invokes the method. The
 * actionPerformed() method does the same thing, allowing this class to implement ActionListener and
 * be used to respond to ActionEvents generated in a GUI or elsewhere. The static parse() method
 * parses a string representation of a method and its arguments.
 */
public class CustomMethodInvocater {
    Method m; // The method to be invoked

    Object target; // The object to invoke it on

    Object[] args; // The arguments to pass to the method

    // An empty array; used for methods with no arguments at all.
    static final Object[] nullargs = new Object[] {};

    /** This constructor creates a Command object for a no-arg method */
    public CustomMethodInvocater(Object target, Method m) {
        this(target, m, nullargs);
    }

    /**
     * This constructor creates a Command object for a method that takes the specified array of
     * arguments. Note that the parse() method provides another way to create a Command object
     */
    public CustomMethodInvocater(Object target, Method m, Object[] args) {
        this.target = target;
        this.m = m;
        this.args = args;
    }

    /**
     * Invoke the Command by calling the method on its target, and passing the arguments. See also
     * actionPerformed() which does not throw the checked exceptions that this method does.
     */
    public Object invoke() throws IllegalAccessException, InvocationTargetException {
        return invoke(args); // Use reflection to invoke the method
    }

    /**
     * Invoke the Command by calling the method on its target, and passing the arguments. See also
     * actionPerformed() which does not throw the checked exceptions that this method does.
     */
    public Object invoke(Object[] arguments) throws IllegalAccessException,
                    InvocationTargetException {
        return m.invoke(target, arguments); // Use reflection to invoke the method
    }

    /**
     * This static method creates a Command using the specified target object, and the specified
     * string. The string should contain method name followed by an optional parenthesized
     * comma-separated argument list and a semicolon. The arguments may be boolean, integer or
     * double literals, or double-quoted strings. The parser is lenient about missing commas,
     * semicolons and quotes, but throws an IOException if it cannot parse the string.
     */
    public static CustomMethodInvocater parse(Object target, String text) throws IOException {
        String methodname; // The name of the method
        ArrayList args = new ArrayList(); // Hold arguments as we parse them.
        ArrayList types = new ArrayList(); // Hold argument types.

        // Convert the string into a character stream, and use the
        // StreamTokenizer class to convert it into a stream of tokens
        StreamTokenizer t = new StreamTokenizer(new StringReader(text));

        // The first token must be the method name
        int c = t.nextToken(); // read a token
        if (c != t.TT_WORD) // check the token type
        throw new IOException("Missing method name for command");
        methodname = t.sval; // Remember the method name

        // Now we either need a semicolon or a open paren
        c = t.nextToken();
        if (c == '(') { // If we see an open paren, then parse an arg list
            for (;;) { // Loop 'till end of arglist
                c = t.nextToken(); // Read next token

                if (c == ')') { // See if we're done parsing arguments.
                    c = t.nextToken(); // If so, parse an optional semicolon
                    if (c != ';') t.pushBack();
                    break; // Now stop the loop.
                }

                // Otherwise, the token is an argument; figure out its type
                if (c == t.TT_WORD) {
                    // If the token is an identifier, parse boolean literals,
                    // and treat any other tokens as unquoted string literals.
                    if (t.sval.equals("true")) { // Boolean literal
                        args.add(Boolean.TRUE);
                        types.add(boolean.class);
                    }
                    else if (t.sval.equals("false")) { // Boolean literal
                        args.add(Boolean.FALSE);
                        types.add(boolean.class);
                    }
                    else { // Assume its a string
                        args.add(t.sval);
                        types.add(String.class);
                    }
                }
                else if (c == '"') { // If the token is a quoted string
                    args.add(t.sval);
                    types.add(String.class);
                }
                else if (c == t.TT_NUMBER) { // If the token is a number
                    int i = (int) t.nval;
                    if (i == t.nval) { // Check if its an integer
                        // Note: this code treats a token like "2.0" as an int!
                        args.add(new Integer(i));
                        types.add(int.class);
                    }
                    else { // Otherwise, its a double
                        args.add(new Double(t.nval));
                        types.add(double.class);
                    }
                }
                else { // Any other token is an error
                    throw new IOException("Unexpected token " + t.sval + " in argument list of "
                                    + methodname + "().");
                }

                // Next should be a comma, but we don't complain if its not
                c = t.nextToken();
                if (c != ',') t.pushBack();
            }
        }
        else if (c != ';') { // if a method name is not followed by a paren
            t.pushBack(); // then allow a semi-colon but don't require it.
        }

        // We've parsed the argument list.
        // Next, convert the lists of argument values and types to arrays
        Object[] argValues = args.toArray();
        Class[] argtypes = (Class[]) types.toArray(new Class[argValues.length]);

        // At this point, we've got a method name, and arrays of argument
        // values and types. Use reflection on the class of the target object
        // to find a method with the given name and argument types. Throw
        // an exception if we can't find the named method.
        Method method;
        try {
            method = target.getClass().getMethod(methodname, argtypes);
        }
        catch (Exception e) {
            throw new IOException("No such method found, or wrong argument " + "types: "
                            + methodname);
        }

        // Finally, create and return a Command object, using the target object
        // passed to this method, the Method object we obtained above, and
        // the array of argument values we parsed from the string.
        return new CustomMethodInvocater(target, method, argValues);
    }
}