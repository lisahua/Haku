package seal.haku.lexicalAnalyser.model.nameNode;

import seal.haku.lexicalAnalyser.model.types.SENTIMENT;
import seal.haku.lexicalAnalyser.model.types.TokenType;

public class NameToken {
	TokenType type;
	String token;
	SENTIMENT sentiment = SENTIMENT.NEUTRAL;
	String fullWord;

	public NameToken(String token) {
		String[] tkns = token.split("_");
		if (tkns.length>1) {
			type = getType(tkns[1]);
		}
		this.token = token;
	}

	public NameToken(String token, String type) {
		this.token = token;
		this.type = getType(type);
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}

	public void setType(String type) {
		this.type = getType(type);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String toString() {
		return token + "_" + type;
	}

	private TokenType getType(String t) {
		switch (t) {
		case "CC":
			return TokenType.CC;
		case "CD":
			return TokenType.CD;
		case "DT":
			return TokenType.DT;
		case "EX":
			return TokenType.EX;
		case "FW":
			return TokenType.FW;
		case "IN":
			return TokenType.IN;
		case "JJ":
			return TokenType.JJ;
		case "JJR":
			return TokenType.JJR;
		case "JJS":
			return TokenType.JJS;
		case "LS":
			return TokenType.LS;
		case "MD":
			return TokenType.MD;
		case "NN":
			return TokenType.NN;
		case "NNS":
			return TokenType.NNS;
		case "NNP":
			return TokenType.NNP;
		case "NNPS":
			return TokenType.NNPS;
		case "PDT":
			return TokenType.PDT;
		case "POS":
			return TokenType.POS;
		case "PRP":
			return TokenType.PRP;
		case "RB":
			return TokenType.RB;
		case "RBR":
			return TokenType.RBR;
		case "RBS":
			return TokenType.RBS;
		case "RP":
			return TokenType.RP;
		case "SYM":
			return TokenType.SYM;
		case "TO":
			return TokenType.TO;
		case "UH":
			return TokenType.UH;
		case "VB":
			return TokenType.VB;
		case "VBD":
			return TokenType.VBD;
		case "VBG":
			return TokenType.VBG;
		case "VBN":
			return TokenType.VBN;
		case "VBP":
			return TokenType.VBP;
		case "VBZ":
			return TokenType.VBZ;
		case "WDT":
			return TokenType.WDT;
		case "WP":
			return TokenType.WP;
		case "WRB":
			return TokenType.WRB;

		}
		return null;
	}

}
