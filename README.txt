
This is CS316 PROJECT 1: Lexical Analyzer coded in Java programming languag.

********************************************************************************************************************************************
This lexical Analyzer program is a modification of the sample lexical analyzer program provided for the project's EBNF.
 * There were more states added to the enum state. There were 9 nine keyword states added as well.
 * The nextState() was modified to reflect the DFA provided for the project.
 * The project description required the recognition of keywords : false, true, int, float, boolean, if, else, while, print.
 * This was achieved by adding a new utility function called checkForKeyword(). In the driver() if the state was determined to be final 
 * the program goes on to check if the state is id. In the case that it is id it check if the string is one of the keywords.
 
*********************************************************************************************************************************************
To run the program from command line
 * 0. Create a directory named CS316Project1. Save the emailed file "LexicalAnalyzer.java" in the directory.
 * 1. Create input files and output files and save it in the directory CS316Project1.
 * 1. Navigate to the directory where CS316Project1 is stored.
 * 2. Type javac LexicalAnalyzer.java
 * 3. Type java LexicalAnalyzer inputFileName.txt outputFileName.txt
 
Note: inputFileName.txt and outputFileName.txt can be substituted for the name of your input and output file that you want to run the prgram for.

*********************************************************************************************************************************************
                                                ***********END***********