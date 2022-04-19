package com.example.intent;

import android.os.Parcel;
import android.os.Parcelable;

public class ObjectClass implements Parcelable {
    int data10;
    String data20;

    public static final Creator<ObjectClass> CREATOR = new Creator<ObjectClass>() {
        @Override
        public ObjectClass createFromParcel(Parcel source) {
            ObjectClass obj = new ObjectClass();
            obj.data10 = source.readInt();
            obj.data20 = source.readString();

            return obj;
        }

        @Override
        public ObjectClass[] newArray(int size) {
            return new ObjectClass[size];
        }
    };

    // 객체 복원을 위해 필요한 정보
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(data10);
        dest.writeString(data20);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
