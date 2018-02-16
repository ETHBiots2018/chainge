package com.example.mcb.genesisapp.Views;

import android.app.Activity;

import com.example.mcb.genesisapp.Repository.SQLite.BasicSQLiteRepo;
import com.example.mcb.genesisapp.State.StateActivity;
import com.example.mcb.genesisapp.State.StateCallback;

/**
 * Created by andreas on 2/15/18.
 */

public class Action {

    public static  BasicSQLiteRepo repo;
    protected StateCallback stateActivity;

    public static final long TKADDRESS = 0; // token distributor (super user / central bank)


    public Action(StateCallback stateActivity){

        this.stateActivity = stateActivity;

        // repo = new BasicSQLiteRepo(stateActivity.getContext());

        repo = (BasicSQLiteRepo) stateActivity.getRepository();
    }


    //Action where an user can rate a website = give evaluation tokens to a website, 1 = positive, 0 = negative
    public static void rateWebsite (int userIndex, int websizeIndex, int rating){

        long userBalance = repo.readUserEval(userIndex);
        long userTrust = repo.readUserTrust(userIndex);

        long websiteBalance = repo.readWebsiteEval(websizeIndex);

        //Determining the amount of Eval a user should give a website, amount depends on trust level

        // TODO Definition of amountEval function to determine the amount of eval depending on trust level
        long wantedTransactionSize =  userTrust*7; // implement naive version // repo.amountEval(userBalance, userTrust);

        //A user can only give evaluation tokens to a website if he owns some
        if(userBalance > 0 ){
            long transactionSize = (wantedTransactionSize < userBalance) ? wantedTransactionSize : userBalance;
            if(rating == 1){
                positiveTransfer(userIndex,websizeIndex, transactionSize);
            } else {
                negativeTransfer(userIndex,websizeIndex, transactionSize);
            }
        }
    }

    //User gives positive rating for a website, transaction of evaluation tokens from user to website
    public static void positiveTransfer(long fromUser, long toWebsite, long amount){
        repo.writeUserEval(fromUser, repo.readUserEval(fromUser)-amount);
        repo.writeWebsiteEval(toWebsite, repo.readWebsiteEval(toWebsite) + amount);
    }

    //Negative rating for a website, both parties lose tokens, transferred to token distributer
    public static void negativeTransfer(long fromUser, long fromWebsite, long amount){

        //Both user and website loses eval tokens
        repo.writeUserEval(fromUser, repo.readUserEval(fromUser)-amount);
        repo.writeWebsiteEval(fromWebsite, repo.readWebsiteEval(fromWebsite) - amount);

        //Transferred to token distributor
        //TODO: define a global variable that defines the address of the token distributor who is just a special user = TKADDRESS
        repo.writeUserEval(TKADDRESS, repo.readUserEval(TKADDRESS) + 2*amount);

    }

	/* Directly computable with SQL

	//Used at the end of every cycle to gather all user owned evaluation tokens
	public static void collectFromUsers(){

	}

	//Degradation of evaluation balance from the websites
	public static void collectFromWebsite(int degradationValue) {

	}

	*/

    //Initializing database values for newly found websites, not necessary for users because of scatter function
    public static void addNewWebsites(){

    }

    //After we gather all the requested evaluation tokens, we redistribute them with respect to user trust level
    public static void scatterToUser(){
		/*PSEUDO code
		The TDK always has always a trust level of 1
		for every user: Determine the eval value that he should receive depending on his trust level
		writeUser(user, defined amount)
		*/
    }

    //Depending on the community rating of each website, we update a user trust level depending on the accuracy of his rating
    public static void updateTrustLevel(){
		/*
		 * Determine the new trustlevel of each user, except for the token distributor who has constant trust level 1
		 * For every visited website of a user we check the difference between received evaluation tokens and losed ones
		 * If the difference between received and losed evaluation tokens is too high, the user's trust level is updated
		 * Increased if part of majority, else he will lose trust
		 *
		 */
    }

    //Initializing the data base value for the trust level balance of a user
    public static void addNewUser(){

    }
}
