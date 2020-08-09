package br.com.navita.api.util;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

public abstract class GenericConverter {

	 /**
	  * Converts a source to a type destination.
	  * 
	  * @param source					The source object
	  * @param typeDestination			The type destination
	  * @return							The object created
	  */
    public static <T, E> E mapper(T source, Class<E> typeDestination) {

         ModelMapper modelMapper = mapper();
         return modelMapper.map(source, typeDestination);

    }

    /**
     * Converts a source to a type destination.
     *
     * @param source				The source object
     * @param destination			The destination object
     * @return						The object created
     */
    public static <T, E> E mapper(T source, E destination) {

         ModelMapper modelMapper = mapper();
         modelMapper.map(source, destination);

         return destination;
    }


    /**
     * Converts a source to a type destination.
     * 
     * @param source				The souce object
     * @return						The object created
     */
    public static <E, T> List<E> mapper(List<T> source, Type destinationType) {

         List<E> model = null;
         if (source != null && destinationType != null) {

              ModelMapper modelMapper = mapper();

              model = modelMapper.map(source, destinationType);
         }

         return model;
    }

    public static <T, E> void convertWithMapping(T source, E destination, PropertyMap<T, E> mapping) {

         if (source != null && destination != null) {

              ModelMapper modelMapper = mapper();

              modelMapper.addMappings(mapping);
              modelMapper.map(source, destination);
         }
    }

    public static <T, E> E convertWithMapping(T source, Class<E> destination, PropertyMap<T, E> mapping) {

         if (source != null && destination != null) {

              ModelMapper modelMapper = mapper();

              modelMapper.addMappings(mapping);
              return modelMapper.map(source, destination);
         }
         return null;
    }

    private static ModelMapper mapper() {
         ModelMapper modelMapper = new ModelMapper();
         modelMapper.getConfiguration()
                 .setPropertyCondition(Conditions.isNotNull())
                 .setMatchingStrategy(MatchingStrategies.STRICT);

         return modelMapper;
    }
}
