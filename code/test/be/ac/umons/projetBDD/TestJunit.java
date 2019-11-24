package be.ac.umons.projetBDD;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
*Cette classe permet de créer les tets junit pour tester les méthodes de BDD
*@author Randy Dauchot & Guillaume Cardoen (étudiant en Sciences informatique)
*/

class TestJunit
{
  @Test
  public void testBDD()
  {
    assertFalse(1==0);
  }
}
