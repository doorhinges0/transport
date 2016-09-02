package com.realtimestudio.transport.model.baisha;

public enum CarEvent {
	M("手动")
	, P("断电")
	, E("开门盗警")
	, D("非法开门")
	, F("非法点火")
	, O("GPS开路")
	, S("超速")
	, T("疲劳驾驶");
	
	private String description;
	private CarEvent(String description){
		this.description = description;
	}
	
	@Override
	public String toString(){
		return description;
	}
	
	public static CarEvent getCarEvent(String str){
		switch(str){
		case "M" : return M;
		case "P" : return P;
		case "E" : return E;
		case "D" : return D;
		case "F" : return F;
		case "O" : return O;
		case "S" : return S;
		case "T" : return T;
		default : return null;
		}
	}

}
