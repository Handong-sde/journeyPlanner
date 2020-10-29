import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
        // Clear all data 
        Fixtures.deleteDatabase();

        // // Check if there are no existing accounts
        // if(Account.count() == 0) {

        //     // Create and save two new accounts
        //     new Account(221, false).save();
        //     new Account(222, false).save();
        // }

        // // Checkk if tehre are no existing customers
        // if (Customer.count() == 0) {
        //     // Find an existing account
        //     Account acc = Account.find("byAccountNumber", 222).first();

        //     // Create and save a new customer
        //     new Customer(acc, 31, "Bob").save();
        // }
    }
 
}