package booking.hotel.converter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import booking.hotel.exception.NoParamsException;
import booking.hotel.util.EntityForSearchRoom;
import booking.hotel.request.RoomSearchRequest;



public class RoomSearchRequestConverter extends  EntityConverter <RoomSearchRequest, EntityForSearchRoom>{
    @Override
    public EntityForSearchRoom convert(RoomSearchRequest request) {

        EntityForSearchRoom entity = new EntityForSearchRoom();

        if(request.getDataOut()==null && request.getDataIn()==null){
            entity.setDataIn(new Timestamp(new Date().getTime()));
        }else{
            entity.setDataIn(request.getDataIn());
            entity.setDataOut(request.getDataOut());
        }


        if(request.getIdComfort().isEmpty()){
            List<Long> list= new ArrayList<>();
            list.add(1L);
            entity.setIdComfort(list);
        }else{
            entity.setIdComfort(request.getIdComfort());
        }

        try{
            if(!request.getPriceMin().equals("false")){
                entity.setPriceMin(Long.valueOf(request.getPriceMin()));
            }
            if(!request.getPriceMax().equals("false")){
                entity.setPriceMax(Long.valueOf(request.getPriceMax()));
            }
            if(!request.getRatingMin().equals("false")){
                entity.setRatingMin(Long.valueOf(request.getRatingMin()));
            }
            if(!request.getRatingMax().equals("false")){
                entity.setRatingMax(Long.valueOf(request.getRatingMax()));
            }
        }catch(NumberFormatException e){
            throw new NoParamsException(e.getMessage());
        }

        if(request.getPrincipleOfPlacement()!=null){
            entity.setPrincipleOfPlacement(request.getPrincipleOfPlacement());
        }

        return doConvert(request,entity);
    }
}
