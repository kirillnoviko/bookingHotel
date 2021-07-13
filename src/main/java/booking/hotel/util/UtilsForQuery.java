package booking.hotel.util;

import booking.hotel.domain.criteria.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UtilsForQuery {

    public <M> String createStringForSearchByParams(Criteria<M> searchRoom){
        List<String> result = new ArrayList<String>();

        for (Map.Entry<M, Object> entry : searchRoom.getCriteria()) {
            if(entry.getValue()!=null ){
                String temp=entry.getKey().toString().toLowerCase() + " = :" + entry.getKey().toString().toLowerCase();
                result.add(temp);
            }
        }

        String queryPartOne="select rm.id,rm.name,rm.price,rm.principle_of_placement,rm.number_room,rm.rating_average " +
                "from room rm where ";

        for(int i=0;i<result.size();i++){

            queryPartOne += result.get(i);
            if( i!=result.size()-1){
                queryPartOne+=" and ";
            }
          /*  if(i==result.size()-1){
                queryPartOne+=" ";
            }*/

        }
        return queryPartOne;
    }

    public <M> String createStringForSearchByDate(Criteria<M> searchData){


        String query=" except select r.id,r.name,r.price,r.principle_of_placement,r.number_room,r.rating_average " +
                "from room r inner join booking b on (r.id=b.id_room)";

        int count=0;
        for (Map.Entry<M, Object> entry : searchData.getCriteria()) {
            if(entry.getValue()!=null ){
                if(count ==0){
                    query+=" where  (:" +  entry.getKey().toString().toLowerCase() + " between data_check_in and data_check_out)";
                }else{
                    query+=" or  (:" +  entry.getKey().toString().toLowerCase() + " between data_check_in and data_check_out)";
                }
                count++;
            }
        }


        return query;
    }

}
