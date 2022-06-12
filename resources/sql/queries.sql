-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id

--:name addb! :! :n
--:doc adds another booking
INSERT INTO rent
(name,org,parea,barea,tarea,hyra,omk,tid,start,stop,notes,kost,kost2)
VALUES (:name, :org, :parea, :barea, :tarea, :hyra, :omk, :tid, :start, :stop, :notes, :kost, :kost2)

--:name getb
--:doc get all entries from rent
SELECT * FROM rent

--:name getrows
--:doc get all rows
select * from rent

--:name cmisc
--:doc create test misc
INSERT INTO misc
(pname,org,pay)
VALUES (:pname, :org, :pay)

--:name deleteb
--:doc delete an id entry
delete from rent where id=:id

-- :name delrow :! :1
-- :doc deletes a row from rent given the id
DELETE FROM rent
WHERE id=:id

-- rental.db.core/addb
