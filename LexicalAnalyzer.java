
/***************************************************************
 * CS-316 Project 1: Lexical Analyzer
 * LexicalAnalyzer.java
 * Purpose: To read a stream of lexemes from input file and to categorize them into tokens and 
 * print in output file, if it find any invalid token will report an error. 
 * 
 * @author Mohammad R Hasan
 * A program that works as a Lex.
 *
 */
//package CS316Project1;

public class LexicalAnalyzer extends IO {
	// The following variables of "IO" class are used:
	//   static int input; the current input character
	//   static char ch; used to convert the variable "a" to the char type whenever necessary

	public static String token;			// holds an extracted token
	public static state currentState;	// the current state of the FA

	public enum state {

		//non-final states    ordinal number
		start,					//0
		dot,					//1
		E,					//2
		EPlusMinus,				//3

		//final states

		//token categories
		id,					//4
		Int,					//5
		Float,				    	//6
		floatE,					//7
		FloatF,					//8


		//arithmetic operators
		add,					//9
		sub,					//10
		mul,					//11
		div,					//12

		//logical operators
		lt,					//13
		le,					//14
		gt,					//15
		ge,					//16
		eq,					//17
		inv,					//18
		neq,					//19
		or,					//20
		and,					//21

		//other punctuations
		LParen,					//22
		RParen,					//23
		LBrace,					//24
		RBrace,					//25
		LBracket,				//26
		RBracket,				//27
		comma,					//28
		colon,					//29
		semicolon,				//30

		//keyword states			//31-39
		kw_false,
		kw_true,
		kw_int,
		kw_float,
		kw_boolean,
		kw_if,
		kw_else,
		kw_while,
		kw_print,

		UNDEF,

	}//end of enum

	/*
	 *  By enumerating the non-final states first and then the final states,
	 *	test for a final state can be done by testing if the state's ordinal number
	 *	is greater than or equal to that of Id.
	 */
	private static boolean isFinal(state st) {

		return ( st.compareTo(state.id )>= 0);// // state id and above is final state
	}// method is Final


	/*
	 *  This is the driver of the FA. 
	 *  If a valid token is found, assigns it to "token" and returns 1.
	 *  If an invalid token is found, assigns it to "token" and returns 0.
	 *  If end-of-stream is reached without finding any non-whitespace character, returns -1.
	 */
	private static int driver(){

		state nextSt;	// the next state of the FA
		token = "";
		currentState = state.start;
		if (Character.isWhitespace(input)) input = getChar(); // get the next non-whitespace character
		if(input == -1) return -1; // end-of-stream is reached

		while(input != -1){ // do the body if "input" is not end-of-stream
			ch = (char) input;
			nextSt = nextState(ch);
			if(nextSt == state.UNDEF){	// The FA will halt.
				if (isFinal(currentState)){
					if(currentState ==state.id){	//check to see if state is ID. If so check to see if the string is a keyword.
						currentState = checkForKeyWord(token);	 //change state to the appropriate keyword's state.
					}else{ }
					return 1; //valid token extracted

				}else{ //if ch is an unexpected character
					token = token+ch;
					input = getNextChar();
					return 0;	//invlaid token found
				}
			}else{ //The FA will move on
				currentState = nextSt;
				token = token + ch;
				input = getNextChar();
			}

		}// end-of-stream is reached while a token is being extracted
		if (isFinal(currentState)) return 1;//valid token extracted
		else return 0;//invalid token found

	}// end driver


	private static state nextState( char c) {
		// Returns the next state of the FA given the input char;
		// if the next state is undefined, UNDEF is returned. This was implemented looking at the DFA diagram provided for the project
		switch (currentState){

		case start:

			if ( Character.isLetter(c) )
				return state.id;
			else if ( Character.isDigit(c) )
				return state.Int;

			//arithmetic operators
			else if ( c == '+' ) 
				return state.add;
			else if ( c == '-' ) 
				return state.sub;
			else if ( c == '*' ) 
				return state.mul;
			else if ( c == '/' ) 
				return state.div;

			//logical operators
			else if ( c == '<' )
				return state.lt;
			else if ( c == '>' ) 
				return state.gt;
			else if ( c == '=' ) 
				return state.eq;
			else if ( c == '!' ) 
				return state.inv;
			else if ( c == '|' ) 
				return state.or;
			else if ( c == '&' ) 
				return state.and;

			//other punctuation
			else if ( c == '(' ) 
				return state.LParen;
			else if ( c == ')' ) 
				return state.RParen;
			else if ( c == '{' ) 
				return state.LBrace;
			else if ( c == '}' ) 
				return state.RBrace;
			else if ( c == '[' ) 
				return state.LBracket;
			else if ( c == ']' ) 
				return state.RBracket;
			else if ( c == '.' ) 
				return state.dot;
			else if ( c == ',' ) 
				return state.comma;
			else if ( c ==  ':' ) 
				return state.colon;
			else if ( c == ';')
				return state.semicolon;

			else 
				return state.UNDEF;

		case id:
			if ( Character.isLetterOrDigit(c) )
				return state.id;
			else
				return state.UNDEF;
		case Int:
			if ( Character.isDigit(c) )
				return state.Int;
			else if ( c == 'f'|| c == 'F')
				return state.FloatF;
			else if ( c == 'e' || c == 'E')
				return state.E;
			else if ( c == '.' )
				return state.Float;
			else
				return state.UNDEF;

		case add:
			if ( Character.isDigit(c) ) 
				return state.Int;
			else if ( c == '.')
				return state.dot;
			else 
				return state.UNDEF;

		case sub:
			if ( Character.isDigit(c) ) 
				return state.Int;
			else if ( c == '.')
				return state.dot;
			else 
				return state.UNDEF;


		case dot:
			if ( Character.isDigit(c) ) 
				return state.Float;
			else 
				return state.UNDEF;


		case Float:
			if ( Character.isDigit(c) ) 
				return state.Float;
			else if ( c == 'f' || c == 'F')
				return state.FloatF;
			else if ( c == 'e' || c == 'E' )
				return state.E;
			else
				return state.UNDEF;
		case E:
			if ( Character.isDigit(c) )
				return state.floatE;
			else if ( c == '+' || c == '-' )
				return state.EPlusMinus;
			else
				return state.UNDEF;

		case EPlusMinus:
			if ( Character.isDigit(c) )
				return state.floatE;
			else
				return state.UNDEF;

		case floatE:
			if ( Character.isDigit(c) )
				return state.floatE;
			else if ( c == 'f' || c == 'F')
				return state.FloatF;
			else
				return state.UNDEF;

		case lt:
			if ( c == '=' ) 
				return state.le;
			else 
				return state.UNDEF;

		case gt:
			if ( c == '=' ) 
				return state.ge;
			else 
				return state.UNDEF;

		case inv:
			if ( c == '=' ) 
				return state.neq;
			else 
				return state.UNDEF;

		default:
			return state.UNDEF;
		}//switch end

	}//method nextState end


	/*
	 * /This function checks if the string that is read is one of the 
	 * 	keywords: false, true, int, float, boolean, if, else, while, print 
	 */
	private static state checkForKeyWord(String s) {
		if ( s.equals("false") ) 
			return state.kw_false;
		else if ( s.equals("true") ) 
			return state.kw_true;
		else if ( s.equals("int") ) 
			return state.kw_int;
		else if ( s.equals("float") ) 
			return state.kw_float;
		else if ( s.equals("boolean") ) 
			return state.kw_boolean;
		else if ( s.equals("if") ) 
			return state.kw_if;
		else if ( s.equals("else") ) 
			return state.kw_else;
		else if ( s.equals("while") ) 
			return state.kw_while;
		else if ( s.equals("print") ) 
			return state.kw_print;

		else return state.id;

	}//end of method checkForKeyWord


	public static void getToken(){

		// Extract the next token using the driver of the FA.
		// If an invalid token is found, issue an error message.

		int i = driver();
		if ( i == 0 )
			displayLine(token + " : Lexical Error, invalid token");

	}//method getToken end


	public static void main(String argv[]){

		// The input and output file names must be passed as argv[0] and argv[1].

		System.out.println("Please check the output file for result.");
		int i;

		setIO( argv[0], argv[1] );

		while ( input != -1 ){ // while "input" is not end-of-stream
			i = driver(); // extract the next token
			if ( i == 1 )
				displayLine( token+"   : "+currentState.toString() );
			else if ( i == 0 )
				displayLine( token+" : Lexical Error, invalid token");
		} 

		closeIO();//close the input and output file
	} // end main

}//end of class LexicalAnalyzer
