package com.mad.password_generator.services;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PasswordGenerationService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordGenerationService.class);
    private final ModelMapper mapper = new ModelMapper();

    /**
     * Méthode principalle pour la génération du mot de passe.
     *
     * @param passwordOptionsRequestDTO
     * @return  Un objet de type 'PasswordOptionsResponseDTO' constituant le mot de passe.
     */
    public PasswordOptionsResponseDTO generate(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
    logger.info("Init generate");

        validateInput(passwordOptionsRequestDTO);

        PasswordOptions passwordOptions = mapper.map(passwordOptionsRequestDTO, PasswordOptions.class);



        logger.info("Fin generate");
        return new PasswordOptionsResponseDTO();
    }



    public void validateInput(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
    logger.info("Init validateInput");

        if (passwordOptionsRequestDTO.getLength() < 4 || passwordOptionsRequestDTO.getLength() > 120) {
            throw new InvalidPasswordOptionsException("La longueur du mot de passe doit être comprise entre 4 et 120 caractères.");
        }
        if (!passwordOptionsRequestDTO.isIncludeUppercase() && !passwordOptionsRequestDTO.isIncludeLowercase() &&
                !passwordOptionsRequestDTO.isIncludeDigits() && !passwordOptionsRequestDTO.isIncludeSpecialChars()) {
            throw new InvalidPasswordOptionsException("Au moins un type de caractère doit être sélectionné.");
        }
        if (passwordOptionsRequestDTO.isRequireEachType() && passwordOptionsRequestDTO.getLength() < 4) {
            throw new InvalidPasswordOptionsException("Impossible de satisfaire `requireEachType` avec une longueur inférieure à 4 caractères."
            );
        }
        logger.info("Fin validateInput");
    }

    public void applyPrefix(String password, String prefix) {

    }
    public void applySuffix(String password, String suffix) {

    }

}
