package conexao;

public class SegurancaBD {
    public static String descriptografar(String sql, int numChave) {
        String cripto1 = " !#'()*+,./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ_`abcdefghijklmnopqrstuvwxyz|~";
        String cripto2 = "+w:r U(6;2/o@1V0n|cyz*,J<.=bmQ5DTGkev'3`PSsd>fIEpZNMFaWugChXOt89_H7ABq!LK4)jl#?xiYR~";


        //gira a string de criptografia
        numChave = Math.abs(numChave);
        numChave = (numChave == 0 ? 1 : numChave);
        if (numChave > sql.length()) {
            numChave = numChave % sql.length();
        }
        for (int i = 0; i < numChave; i++) {
            cripto2 = cripto2.substring(1) + cripto2.substring(0, 1);
        }

        String retorno = "";
        for (int i = 0; i < sql.length(); i++) {
            int pos = cripto2.indexOf(sql.substring(i, i+1));
            if (pos<0) {
                retorno += sql.substring(i, i+1);
            } else {
                retorno += cripto1.substring(pos, pos+1);
            }
        }

        return retorno;
    }

}
