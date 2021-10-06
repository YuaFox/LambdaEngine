# LambdaEngine
LambdaEngine is an engine for a programming language I created.

This project aims to help create a better user experience letting users run code in a safe environment.
You can add custom objects and functions in an easy way.

This language started as a calculator with function support.

This project comes with a main to test the base language.

You can run it with `java -jar <executable> < input_file`

## Sample code
```
a=list(0);
a.put(1);
a.put(3);
a.put(-2);
a.put(4);

i=0;
max=a.size(0);

while(
    {i < max},
    {
        if(
            {a.get(i) > 0},
            {print(a.get(i))},
            {print(":(")}
        );
        i = i + 1;
    }
)
```

## The programming language

### Hello world!
``print("Hello World!")``

### Variables and types
You can assign an object to a variable using te assign operator `=`

Number: `number = 5`

String: `text = "hello!"`

#### List
Create a list: `l = list(0)`

Put an element on a list: `l.put(element)` or `l.put(index, element)`

Get an element on a list: `l.get(index)`

Get how many elements are in a list `l.size(0)`

#### Map
Create a map: `m = map(0)`

#### Function block
Create a function: `f = { <code here> }`

Run a function: `f(0) or f(arg1, arg2)`

A variable list called args will be created when calling a function.
There is not return keyword. The last expression is your return.

### Conditionals and loops

#### Conditional
If function block f is true or 1 function block a will be executed, if not, function block b will be executed instead.
 
`if(f, a, b)`

#### Loop
If function block f is true or 1 a function block a will be executed until f is false or 0.

`while(f, a)`