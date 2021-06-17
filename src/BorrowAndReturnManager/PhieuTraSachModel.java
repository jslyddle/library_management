package BorrowAndReturnManager;


public class PhieuTraSachModel {
	int maphieutrasach;
	int maphieumuonsach;
	String ngaytra;
	int songaymuon;
	double tienphatkynay;
	double tiennokynay;
	
	public PhieuTraSachModel(int MaPhieuTraSach, int MaPhieuMuonSach, String NgayTra, int SoNgayMuon, double TienPhatKyNay, double TienNoKyNay) {
		this.maphieutrasach = MaPhieuTraSach;
		this.maphieumuonsach = MaPhieuMuonSach;
		ngaytra = NgayTra;
		songaymuon = SoNgayMuon;
		tienphatkynay = TienPhatKyNay;
		tiennokynay = TienNoKyNay;
	}
	
	public int getMAPHIEUTRASACH() {return maphieutrasach;}
	
	public int getMAPHIEUMUONSACH() {return maphieumuonsach;}
	
	public String getNGAYTRA() {return ngaytra;}
	
	public int getSONGAYMUON() {return songaymuon;}
	
	public double getTIENPHATKYNAY() {return tienphatkynay;}

	public double getTIENNOKYNAY() {return tiennokynay;}

}
