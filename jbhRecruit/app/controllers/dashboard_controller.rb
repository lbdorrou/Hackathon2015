class DashboardController < ApplicationController

  before_action :authenticate_user!

  def home
    if(current_user.admin)
      @applications = Apply.all;

    else
      @applications = Apply.select{|x| x.user == current_user.email };
      @jobs = Job.all;

    end
  end
end
