
INSERT INTO user (user_id, name, vat, counter_id, address, post_code, phone, date_of_birth, balance) VALUES
(UUID_TO_BIN('7f31ab45-8e27-4a62-bd1e-9641d75bde22', true), 'ΔΗΜΗΤΡΙΟΣ ΔΗΜΗΤΡΙΟΥ', 123456789, 9192939495, 'ΑΛΕΞΑΝΔΡΟΥ 1', '12 345', '6988888888', '1997-01-01', 10000),
(UUID_TO_BIN('a912fe63-2e4b-4d09-baef-c0e9e83d7254', true), 'ΜΑΡΙΑ ΠΑΠΑΔΟΠΟΥΛΟΥ', 987654321, 9493949596, 'ΚΥΡΙΑΚΟΥ 10', '54321', '6912345678', '1985-12-05', 10000),
(UUID_TO_BIN('f5a8d739-1eab-4397-9a35-86f0d3e29a46', true), 'ΓΙΩΡΓΟΣ ΝΙΚΟΛΑΟΥ', 567890123, 9596979899, 'ΠΛΑΤΕΙΑ ΕΛΕΥΘΕΡΙΑΣ 5', '23435', '6945678901', '1990-06-17', 10000),
(UUID_TO_BIN('4dc55181-1dbf-4efc-9c0d-5cf63e57e3c2', true), 'ΕΛΕΝΗ ΑΘΑΝΑΣΙΟΥ', 345678901, 9998989798, 'ΠΑΡΘΕΝΩΝΟΣ 20', '23 345', '6978901234', '1982-09-22', 10000),
(UUID_TO_BIN('b27d7c03-0ef8-4751-9e6b-92f2c812913d', true), 'ΑΝΔΡΕΑΣ ΜΙΧΑΗΛΙΔΗΣ', 901234567, 9898999091, 'ΑΓΙΟΥ ΑΝΤΩΝΙΟΥ 15', '22333', '6990123456', '1993-03-09', 10000),
(UUID_TO_BIN('e9b5b8c6-77f6-4859-9ef5-872c1329e8f7', true), 'ΣΟΦΙΑ ΠΑΠΑΣΤΑΥΡΟΥ', 234567890, 9091929394, 'ΚΟΛΟΚΟΤΡΩΝΗ 8', '12123', '6923456789', '1988-07-12', 10000);

INSERT INTO operator (id, name, password, username)  VALUES
(UUID_TO_BIN('3f06af63-a93c-11e4-9797-00505690773f', true),'Iris online','$2a$12$Y1DiGhxUFlztUWMXGvgnF.dcirJw3RKf77KrXGlFzxO2uZ3j4BQDa','iris_web_app'),
(UUID_TO_BIN('cff96603-017f-445e-be20-326e0b2a1f59', true),'Iris mobile','$2a$12$mDF/kett6y2NUpGVp2w0guyzGMF4WWfA/XRwR9ktIDJQjQJxaMtOm','iris_mobile_app'),
(UUID_TO_BIN('08182796-c0dd-43b6-bad7-b04946c9d28f', true),'Viva wallet','$2a$12$bToKD8LtGU27pNn2AFxhmuyXfTlne4pLO1Y21.RlcJB0HnIfmcOEC','viva_wallet_app'),
(UUID_TO_BIN('523c8857-4c27-44f5-8513-e08479ee2ae2', true),'Balance Admin','$2a$12$6VciOdXXcf.0jPWhscO.1.dlot3BG7wwNBWpCxX4xg.aatnMbsUue','balance_admin');


INSERT INTO role (id,name) VALUES
((UUID_TO_BIN('b80ab76d-586e-427d-9137-0574562d6c92', true)),'ROLE_OPERATOR'),
((UUID_TO_BIN('8c2e5e82-6d3c-4164-97a3-46d12a974dc0', true)),'ROLE_ADMIN');

INSERT INTO operator_roles (operator_id, role_id) VALUES
(UUID_TO_BIN('3f06af63-a93c-11e4-9797-00505690773f', true), UUID_TO_BIN('b80ab76d-586e-427d-9137-0574562d6c92', true)),
(UUID_TO_BIN('cff96603-017f-445e-be20-326e0b2a1f59', true), UUID_TO_BIN('b80ab76d-586e-427d-9137-0574562d6c92', true)),
(UUID_TO_BIN('08182796-c0dd-43b6-bad7-b04946c9d28f', true), UUID_TO_BIN('b80ab76d-586e-427d-9137-0574562d6c92', true)),
(UUID_TO_BIN('523c8857-4c27-44f5-8513-e08479ee2ae2', true), UUID_TO_BIN('8c2e5e82-6d3c-4164-97a3-46d12a974dc0', true));