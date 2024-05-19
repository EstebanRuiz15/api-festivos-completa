package api.api_diasfestivos.aplicacion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class CalcularFestivo {

    public String calcular(int anio, int mes, int dia) {
        String respuesta = "";

        Date fecha = new Date(anio - 1900, mes - 1, dia);
        Date[] vectorF = new Date[13];

        vectorF = diasfestivos(fecha);
        respuesta = comparar(vectorF, fecha);

        return respuesta;
    }

    public static Date getDomingoRamos(int año) {
        int a = año % 19;
        int b = año % 4;
        int c = año % 7;
        int d = (19 * a + 24) % 30;
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;

        int dia = 15 + dias;
        int mes = 3;

        return new Date(año - 1900, mes - 1, dia);
    }

    public static Date agregarDias(Date fecha, int dias) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        calendario.add(Calendar.DATE, dias);
        return calendario.getTime();
    }

    public static Date siguienteLunes(Date fecha) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        if (calendario.get(Calendar.DAY_OF_WEEK) > Calendar.MONDAY) {
            fecha = agregarDias(fecha, 9 - calendario.get(Calendar.DAY_OF_WEEK));
        } else {
            if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                fecha = fecha;
            } else {
                fecha = agregarDias(fecha, 1);
            }
        }
        return fecha;
    }

    public static Date[] diasfestivos(Date fecha) {
        Date[] fechass = new Date[13];
        Date santoreyes = siguienteLunes(new Date(fecha.getYear(), 0, 6));
        fechass[0] = santoreyes;
        Date sanjose = siguienteLunes(new Date(fecha.getYear(), 2, 19));
        fechass[1] = sanjose;
        int anio = (fecha.getYear() + 1900);

        Date domingoRamos = getDomingoRamos(anio);
        Date domingoPascua = agregarDias(domingoRamos, 7);

        Date juevessanto = agregarDias(domingoPascua, -3);
        fechass[2] = juevessanto;
        Date viernesSanto = agregarDias(domingoPascua, -2);
        fechass[3] = viernesSanto;
        fechass[4] = domingoPascua;
        Date ascencion = agregarDias(domingoPascua, 40);
        Date ascenciondelSenior = siguienteLunes(ascencion);
        fechass[5] = ascenciondelSenior;
        Date Corpus = siguienteLunes(agregarDias(domingoPascua, 61));
        fechass[6] = Corpus;
        Date sagradoCorazon = siguienteLunes(agregarDias(domingoPascua, 68));
        fechass[7] = sagradoCorazon;
        Date sanPedroyPablo = siguienteLunes(new Date(fecha.getYear(), 5, 29));
        fechass[8] = sanPedroyPablo;
        Date asuncionVirgen = siguienteLunes(new Date(fecha.getYear(), 7, 15));
        fechass[9] = asuncionVirgen;
        Date diaRaza = siguienteLunes(new Date(fecha.getYear(), 9, 12));
        fechass[10] = diaRaza;
        Date todosSantos = siguienteLunes(new Date(fecha.getYear(), 10, 1));
        fechass[11] = todosSantos;
        Date independenciaCartagena = siguienteLunes(new Date(fecha.getYear(), 10, 11));
        fechass[12] = independenciaCartagena;

        return fechass;
    }

    public static String comparar(Date[] fechas, Date fecha) {
        SimpleDateFormat f1 = new SimpleDateFormat("EEEE dd/MM/yyyy");
        String respuesta = "la fecha " + f1.format(fecha) + " No es festivo";

        for (int i = 0; i < fechas.length; i++) {

            if (fecha.equals(fechas[i])) {

                respuesta = "la fecha " + f1.format(fecha) + " Si es Festivo";
            }
        }

        return respuesta;

    }
}
