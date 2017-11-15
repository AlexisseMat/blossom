package fr.blossom.core.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.assertj.core.util.Preconditions;

class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private UserRepository userRepository;

  public UniqueEmailValidator(UserRepository userRepository) {
    Preconditions.checkNotNull(userRepository);
    this.userRepository = userRepository;
  }

  public void initialize(UniqueEmail constraint) {
  }

  public boolean isValid(String email, ConstraintValidatorContext context) {
    return email != null && !userRepository.findOneByEmail(email).isPresent();
  }

}
