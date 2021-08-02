Tasks Java:

1. Prepare and calculate formula

We have a method which receives a string represents math formula with simple operators (add, divide, multiply and subtract)
and operands can be integer numbers (1,10,55...) and unknown variables (a,b,c...)
Also some operands can be in parentheses which means they must be calculated at first via general Math rules.
The main point is we don't have all operands as numbers, some of them can be variables and we don't know theis values at this moment.
Imagine, formula can be super huge and difficult and parsing it requires a lot of time, and we decided separate formula parsing and calculating
with all variables to two different methods.

Design and implement 2 methods:
1. method which parse string formula and prepare some object which ready to fast calculation
2. method which calculates final value when all variables we get as input

Examples of formula:
(a + b) * 20 - c
12 - 5 * 6 / (a - 12 * (b + 5))