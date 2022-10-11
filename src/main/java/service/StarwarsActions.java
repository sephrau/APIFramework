package service;

public class StarwarsActions {

    StarwarsService starwarsService;

    public StarwarsActions(StarwarsService starwarsService){
        this.starwarsService = starwarsService;
    }

    public int getAverageStarwarsSeries(){
        return starwarsService.getTotalCountFromStarWarsSeries() / starwarsService.getTotalPeople();
    }

}
