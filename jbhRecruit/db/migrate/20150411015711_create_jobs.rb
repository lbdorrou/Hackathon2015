class CreateJobs < ActiveRecord::Migration
  def change
    create_table :jobs do |t|
      t.text :jobId
      t.text :title
      t.text :department
      t.text :category
      t.text :description
      t.integer :siteID
      t.date :postingDate
      t.boolean :fullTime
      t.text :shift
      t.timestamps null: false
    end
  end
end
