package app.jasonhk.hkiit.fifteentwenty.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public record Hands(boolean left, boolean right) implements Parcelable
{
    public static final Creator<Hands> CREATOR = new Creator<>()
    {
        @Override
        public Hands createFromParcel(Parcel in)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            {
                return new Hands(in.readBoolean(), in.readBoolean());
            }
            else
            {
                return new Hands((in.readByte() != 0), (in.readByte() != 0));
            }
        }

        @Override
        public Hands[] newArray(int size)
        {
            return new Hands[size];
        }
    };

    public int getValue()
    {
        return ((left ? 5 : 0) + (right ? 5 : 0));
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            out.writeBoolean(left);
            out.writeBoolean(right);
        }
        else
        {
            out.writeByte((byte) (left ? 1 : 0));
            out.writeByte((byte) (right ? 1 : 0));
        }
    }
}
