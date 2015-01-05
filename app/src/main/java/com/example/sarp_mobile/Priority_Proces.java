package com.example.sarp_mobile;

public class Priority_Proces extends Proces
{
    /* Atribut, ki je značilen samo za določene algoritme */
    private int prioriteta_proc;

    /*Pomožne spremenljivke v razredu*/
    /* Minimalna in maksimalna vrednost za prioriteto procesa. 1 - najvišja */
    /* prioriteta procesa , 3 - najnižja prioriteta procesa. */
    int min_p = 1;
    int max_p = 5;

    /* Privzeti konstruktor. V njem podedujemo atribute razreda Proces */
    /* in generiramo prioriteto procesa */
    public Priority_Proces()
    {
        super();
        this.prioriteta_proc = gen_prioriteta();
    }

    public Priority_Proces(Object n){}

    public Priority_Proces(String imeProcesa, int casDospetjaProcesa, int casTrajanjaProcesa, int prioritetaProcesa)
    {
        super.set_ime_proc(imeProcesa);
        super.set_cas_dospetja_proc(casDospetjaProcesa);
        super.set_trajanje_proc(casTrajanjaProcesa);
        prioriteta_proc = prioritetaProcesa;
    }

    /* get in set metodi za atribut prioriteta */
    protected void set_prioriteta_proc(int prioriteta_proc)
    {
        this.prioriteta_proc = prioriteta_proc;
    }

    protected int get_prioriteta_proc()
    {
        return this.prioriteta_proc;
    }

    /* Generira naključno prioriteto procesa na intervalu 0 - 5 [0, min_p, max_p] */
    private int gen_prioriteta()
    {
        if(Math.random() < 0.15)
            return 0;

        else
            return min_p + (int)(Math.random() * ((max_p - min_p) + 1));
    }

    @Override
    public String toString()
    {
        return super.toString() + "\t" + this.get_prioriteta_proc();
    }

}