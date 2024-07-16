package oop.studentmanagement.util;

public class Nilai {
    private String nim;
    private String kodeMatkul;
    private String namaMatkul;
    private int sks;
    private float nilaiTugas;
    private float nilaiUts;
    private float nilaiUas;
    private String nilaiAkhir;

    public Nilai(String nim, String kodeMatkul, String namaMatkul, int sks, float nilaiTugas, float nilaiUts, float nilaiUas, String nilaiAkhir) {
        this.nim = nim;
        this.kodeMatkul = kodeMatkul;
        this.namaMatkul = namaMatkul;
        this.sks = sks;
        this.nilaiTugas = nilaiTugas;
        this.nilaiUts = nilaiUts;
        this.nilaiUas = nilaiUas;
        this.nilaiAkhir = nilaiAkhir;
    }

    public String getNim() {
        return nim;
    }

    public String getKodeMatkul() {
        return kodeMatkul;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public int getSks() {
        return sks;
    }

    public float getNilaiTugas() {
        return nilaiTugas;
    }

    public float getNilaiUts() {
        return nilaiUts;
    }

    public float getNilaiUas() {
        return nilaiUas;
    }

    public String getNilaiAkhir() {
        return nilaiAkhir;
    }
}
