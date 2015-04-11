class CreateJobs < ActiveRecord::Migration
  def change
    create_table :jobs do |t|
      t.text :title
      t.text :description
      t.text :category
      t.integer :siteID
      t.date :postingDate
      t.boolean :fullTime
      t.integer :shift

      t.timestamps null: false
    end
  end
end
