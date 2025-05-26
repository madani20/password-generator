package com.mad.password_generator.services;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.models.PasswordOptions;
import org.modelmapper.ModelMapper;


public class PasswordGenerationService {
    private final ModelMapper mapper = new ModelMapper();

    /**
     * Méthode principalle pour la génération du mot de passe.
     *
     * @param passwordOptionsRequestDTO
     * @return  Un objet de type 'PasswordOptionsResponseDTO' constituant le mot de passe.
     */
    public PasswordOptionsResponseDTO generate(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {

        validateInput(passwordOptionsRequestDTO);

        PasswordOptions passwordOptions = mapper.map(passwordOptionsRequestDTO, PasswordOptions.class);




        return new PasswordOptionsResponseDTO();
    }



    public void validateInput(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {

    }
    public void applyPrefix(String password, String prefix) {

    }
    public void applySuffix(String password, String suffix) {

    }
}
