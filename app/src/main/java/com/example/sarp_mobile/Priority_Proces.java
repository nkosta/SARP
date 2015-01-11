package com.example.sarp_mobile;

/**
 * Extends normal Process class and adds priority handling
 */
public class Priority_Proces extends Proces {
    /* Atribut, ki je značilen samo za določene algoritme */
    private int prioriteta_proc;

    /*Pomožne spremenljivke v razredu*/
    /* Minimalna in maksimalna vrednost za prioriteto procesa. 1 - najvišja */
    /* prioriteta procesa , 3 - najnižja prioriteta procesa. */
    int min_p = 1;
    int max_p = 5;

    /* Privzeti konstruktor. V njem podedujemo atribute razreda Proces */
    /* in generiramo prioriteto procesa */

    /**
     * Use super to initialize Process super class
     * Generates randomly a priority value
     */
    public Priority_Proces() {
        super();
        this.prioriteta_proc = gen_prioriteta();
    }

    /**
     * Empty constructor
     *
     * @param n
     */
    public Priority_Proces(Object n) {
    }

    /**
     * Instances instance with suplied parameters
     *
     * @param imeProcesa         Process name
     * @param casDospetjaProcesa Process start time
     * @param casTrajanjaProcesa process duration
     * @param prioritetaProcesa  process priority
     */
    public Priority_Proces(String imeProcesa, int casDospetjaProcesa, int casTrajanjaProcesa, int prioritetaProcesa) {
        super.set_ime_proc(imeProcesa);
        super.set_cas_dospetja_proc(casDospetjaProcesa);
        super.set_trajanje_proc(casTrajanjaProcesa);
        prioriteta_proc = prioritetaProcesa;
    }

    /* get in set metodi za atribut prioriteta */

    /**
     * Setter for process priority
     *
     * @param prioriteta_proc new value for priority
     */
    protected void set_prioriteta_proc(int prioriteta_proc) {
        this.prioriteta_proc = prioriteta_proc;
    }

    /**
     * Getter for Process priority
     *
     * @return current Process priority
     */
    protected int get_prioriteta_proc() {
        return this.prioriteta_proc;
    }

    /* Generira naključno prioriteto procesa na intervalu 0 - 5 [0, min_p, max_p] */
    private int gen_prioriteta() {
        if (Math.random() < 0.15)
            return 0;

        else
            return min_p + (int) (Math.random() * ((max_p - min_p) + 1));
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + this.get_prioriteta_proc();
    }

}