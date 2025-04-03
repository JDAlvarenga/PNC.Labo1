package Models;

import java.util.Random;

public class EpicCode implements Cloneable
{
    private String code;
    private EpicCode() {}
    public EpicCode(String code)
    {
        this.code = code;
    }

    public static EpicCode NewCode()
    {
        Random ran = new Random();
        ran.nextInt(10);

        var c = "ZNH-" +
                ran.nextInt(10)+
                (char)('A' + ran.nextInt(25))+
                ran.nextInt(10)+
                "-MD-"+
                (char)('A' + ran.nextInt(25))+
                ran.nextInt(10);

        return new EpicCode(c);
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof EpicCode epicCode)) return false;

        return code.equals(epicCode.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public EpicCode clone() {
        try {
            EpicCode clone = (EpicCode) super.clone();
            clone.code = this.code;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}