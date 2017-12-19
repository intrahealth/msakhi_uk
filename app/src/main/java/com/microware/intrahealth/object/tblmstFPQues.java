package com.microware.intrahealth.object;

public class tblmstFPQues {
	private int Q_id;
	private int grp;
	private int q_type;
	private String ftext;
	private int y_qid;
	private int n_qid;
	private String oinfo;
	private int def;
	private int finishid;
	private int pans;
	private int qvid;
	private int dispseq;
	private String oprompt;
	private String  Ans;
	private String  Audio_FileName;

	public String getAudio_FileName() {
		return Audio_FileName;
	}

	public void setAudio_FileName(String audio_FileName) {
		Audio_FileName = audio_FileName;
	}

	public String getAns() {
		return Ans;
	}

	public void setAns(String ans) {
		Ans = ans;
	}

	public int getQ_id() {
		return Q_id;
	}

	public void setQ_id(int q_id) {
		Q_id = q_id;
	}

	public int getGrp() {
		return grp;
	}

	public void setGrp(int grp) {
		this.grp = grp;
	}

	public int getQ_type() {
		return q_type;
	}

	public void setQ_type(int q_type) {
		this.q_type = q_type;
	}

	public String getFtext() {
		return ftext;
	}

	public void setFtext(String ftext) {
		this.ftext = ftext;
	}

	public int getY_qid() {
		return y_qid;
	}

	public void setY_qid(int y_qid) {
		this.y_qid = y_qid;
	}

	public int getN_qid() {
		return n_qid;
	}

	public void setN_qid(int n_qid) {
		this.n_qid = n_qid;
	}

	public String getOinfo() {
		return oinfo;
	}

	public void setOinfo(String oinfo) {
		this.oinfo = oinfo;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getFinishid() {
		return finishid;
	}

	public void setFinishid(int finishid) {
		this.finishid = finishid;
	}

	public int getPans() {
		return pans;
	}

	public void setPans(int pans) {
		this.pans = pans;
	}

	public int getQvid() {
		return qvid;
	}

	public void setQvid(int qvid) {
		this.qvid = qvid;
	}

	public int getDispseq() {
		return dispseq;
	}

	public void setDispseq(int dispseq) {
		this.dispseq = dispseq;
	}

	public String getOprompt() {
		return oprompt;
	}

	public void setOprompt(String oprompt) {
		this.oprompt = oprompt;
	}

}
