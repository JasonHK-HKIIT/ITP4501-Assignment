package app.jasonhk.hkiit.fifteentwenty.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpponentChoices implements Parcelable
{
    public static final Creator<OpponentChoices> CREATOR = new Creator<>()
    {
        @Override
        public OpponentChoices createFromParcel(Parcel in)
        {
            return new OpponentChoices(in);
        }

        @Override
        public OpponentChoices[] newArray(int size)
        {
            return new OpponentChoices[size];
        }
    };

    @JsonProperty("left")
    private int handLeft;

    @JsonProperty("right")
    private int handRight;

    @JsonProperty("guess")
    private int guess;

    @SuppressWarnings("unused")
    public OpponentChoices() {}

    protected OpponentChoices(Parcel in)
    {
        handLeft = in.readInt();
        handRight = in.readInt();
        guess = in.readInt();
    }

    public Hands toHands()
    {
        return new Hands((handLeft == 5), (handRight == 5));
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags)
    {
        out.writeInt(handLeft);
        out.writeInt(handRight);
        out.writeInt(guess);
    }

    @Override
    public String toString()
    {
        return "OpponentChoices{" +
                "handLeft=" + handLeft +
                ", handRight=" + handRight +
                ", guess=" + guess +
                '}';
    }
}
