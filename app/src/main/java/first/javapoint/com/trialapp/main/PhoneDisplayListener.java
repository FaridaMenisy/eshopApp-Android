package first.javapoint.com.trialapp.main;

import first.javapoint.com.trialapp.responseDTO.PhonesResponse;
import first.javapoint.com.trialapp.responseDTO.TabletsResponse;

public interface PhoneDisplayListener {
    //id of item to be removed and poistion  of the item in the holder
    void onItemDeleted(int id , int poistion);
   // void onItemAdded();
    void onItemUpdated(PhonesResponse phonesResponse,int poistion);
    void onTabletItemUpdated(TabletsResponse tabletsResponse ,int poistion);
}
