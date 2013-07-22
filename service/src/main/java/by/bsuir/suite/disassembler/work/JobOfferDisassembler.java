package by.bsuir.suite.disassembler.work;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.disassembler.person.PersonJobOfferDisassembler;
import by.bsuir.suite.domain.work.JobOffer;
import by.bsuir.suite.dto.person.PersonJobOfferDto;
import by.bsuir.suite.dto.work.JobOfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Matveyenka Denis
 * Date: 20.07.13
 */
@Component
public class JobOfferDisassembler extends BaseDisassembler<JobOfferDto, JobOffer> {

    @Autowired
    private PersonJobOfferDisassembler personJobOfferDisassembler;

    @Override
    public JobOfferDto disassemble(JobOffer object) {
        JobOfferDto jobOfferDto = new JobOfferDto();
        jobOfferDto.setId(object.getId());
        jobOfferDto.setDate(object.getDate());
        jobOfferDto.setDescription(object.getDescription());
        jobOfferDto.setHours(object.getHours());
        jobOfferDto.setActive(object.isActive());
        jobOfferDto.setNumberOfPeoples(object.getNumberOfPeople());
        if (object.getPersons() != null) {
            Set<PersonJobOfferDto> persons =
                    new HashSet<PersonJobOfferDto>(personJobOfferDisassembler.disassembleToList(object.getPersons()));
            jobOfferDto.setPersonDtos(persons);
            jobOfferDto.setPersonJobOfferSize(persons.size());
        }

        return jobOfferDto;
    }
}
