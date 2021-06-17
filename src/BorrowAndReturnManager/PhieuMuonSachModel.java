package BorrowAndReturnManager;


public class PhieuMuonSachModel {
	int maphieumuonsach;
	int madocgia;
	int masach;
	String ngaymuon;
	
	public PhieuMuonSachModel(int MaPhieuMuonSach, int MaDocGia, int MaSach, String NgayMuon) {
		this.maphieumuonsach = MaPhieuMuonSach;
		this.madocgia = MaDocGia;
		this.masach = MaSach;
		this.ngaymuon = NgayMuon;
	}
	
	public int getMAPHIEUMUONSACH() {return maphieumuonsach;}
	
	public int getMADOCGIA() {return madocgia;}
	
	public int getMASACH() {return masach;}
	
	public String getNGAYMUON() {return ngaymuon;}

}
