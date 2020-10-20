# Description
This repository is the for the *fil rouge* of the course MAPD.

# How to run the code ? 
This project builds with Maven. Thus, the structure is very clear. Test cases are in the [test](src/test) file.

# A little change from the [original design](ClassDiagram.png)
During the implementation, we found that the original design had some problems in the interface. 
Especially in the design of certain **function signatures**, 
we added more parameters who were supposed to be taken as an input from keyboard. 
However, the original idea is hard to be tested, so we changed our design.