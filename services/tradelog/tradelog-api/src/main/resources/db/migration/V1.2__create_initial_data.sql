INSERT INTO action (name)
VALUES ('SELL'),
       ('BUY');


INSERT INTO action_type (name)
VALUES ('STOCK'),
       ('CALL_OPTION'),
       ('PUT_OPTION');


INSERT INTO simple_option (transaction_id, transaction_fk, account_fk, date, symbol, stock_price, strike_price, expiry_date, implied_volatility, implied_volatility_hist, profit_probability, contract_number, premium, action_fk, action_type_fk, broker_fees, mark)
VALUES
       ('90ef0965-0eba-4854-9ab5-12ef21be6464', null, 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-10-17 21:00:00.000000 +11:00', 'MAT', 14.83, 14.0, '2018-11-16 21:00:00.000000 +11:00', 53.6, null, 64.56, 1, 0.55, 'SELL', 'PUT_OPTION', 0, 'A1'),
       ('b8480fe6-f4ce-4375-b508-feedbf9d3977', '90ef0965-0eba-4854-9ab5-12ef21be6464', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-03 21:00:00.000000 +11:00', 'MAT', 0, 14.0, '2018-11-16 21:00:00.000000 +11:00', null, null, null, 1, 0.30, 'BUY', 'PUT_OPTION', 0, 'A1');

INSERT INTO simple_option (transaction_id, transaction_fk, account_fk, date, symbol, stock_price, strike_price, expiry_date, implied_volatility, implied_volatility_hist, profit_probability, contract_number, premium, action_fk, action_type_fk, broker_fees, mark)
VALUES
       ('4464bd1b-26e4-4e65-92a6-7f91d8c5eb11', null, 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-08 21:00:00.000000 +10:00', 'TWTR', 34.79, 35, '2018-12-21 21:00:00.000000 +10:00', 41.8, null, null, 2, 2.11, 'SELL', 'PUT_OPTION', 0, 'A1'),
       ('5e4eb483-41ca-4c0c-8390-3df0137a1ac2', '4464bd1b-26e4-4e65-92a6-7f91d8c5eb11', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-12-12 21:00:00.000000 +10:00', 'TWTR', 0, 35, '2018-12-21 21:00:00.000000 +10:00', null, null, null, 2, 0.70, 'BUY', 'PUT_OPTION', 0, 'A1');

INSERT INTO simple_option (transaction_id, transaction_fk, account_fk, date, symbol, stock_price, strike_price, expiry_date, implied_volatility, implied_volatility_hist, profit_probability, contract_number, premium, action_fk, action_type_fk, broker_fees, mark)
VALUES
       ('654af390-4205-4f41-b210-38bc616d8e5e', null, 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-11-08 21:00:00.000000 +10:00', 'CYBR', 70.63, 70, '2018-12-21 21:00:00.000000 +10:00', 55.2, null, null, 1, 4.35, 'SELL', 'PUT_OPTION', 0, 'A1'),
       ('f3040f85-6940-4f50-9346-205ac9917fd6', '654af390-4205-4f41-b210-38bc616d8e5e', 'd79ec11a-2011-4423-ba01-3af8de0a3e14', '2018-12-12 21:00:00.000000 +10:00', 'CYBR', 0, 70, '2018-12-21 21:00:00.000000 +10:00', null, null, null, 1, 1.53, 'BUY', 'PUT_OPTION', 0, 'A1');


-- 31 Oct 2018	PGR	A1	60.87	27.4%		SELL OPTION	CASH SECURED  PUT	70.0	Nov 16	1	2.50		250	6,750	3.70%	1.2	13 Nov 2018	BUY	-0.38	3.14%	212.00		212.00	13d
-- 31 Oct 2018	HIIQ	A1	48.96	74.2%		SELL OPTION	CASH SECURED  PUT	45.0	Nov 16	1	1.85		185	4,315	4.29%		17 Nov 2018	BUY ASSIGN	0.00	4.29%	185.00	0.31	184.69	17d
-- 14 Nov 2018	CIEN	A1	33.31	47%		SELL OPTION	CASH SECURED  PUT	32.0	Dec 21	2	1.40		280	6,120	4.58%	0.2	13 Dec 2018	BUY	-0.20	3.92%	240.00		240.00	29d
-- 15 Nov 2018	BEAT	A1	60.94	46.4%		SELL OPTION	CASH SECURED  PUT	60.0	Dec 21	1	3.00		300	5,700	5.26%	0.60	28 Nov 2018	BUY	-0.60	4.21%	240.00		240.00	13d
-- 20 Nov 2018	NOMD	A1	19.55	24.7%	30.70%	SELL OPTION	CASH SECURED  PUT	20.0	Dec 21	3	0.80		240	5,760	4.17%	3	21 Dec 2018	BUY ASSIGN	0.00	4.17%	240.00		240.00	31d
-- 23 Nov 2018	ABT	A1	68.53	23.2%	26.70%	SELL OPTION	CASH SECURED  PUT	66.0	Dec 21	1	0.92		92	6,508	1.41%	0.22	28 Nov 2018	BUY	-0.22	1.08%	70.00		70.00	5d
-- 29 Nov 2018	FOXF	A1	64.68	47.5%	68.70%	SELL OPTION	CASH SECURED  PUT	60.0	Dec 21	1	1.45		145	5,855	2.48%	0.65	11 Dec 2018	BUY	-0.65	1.37%	80.00		80.00	12d
-- 4 Dec 2018	HIIQ	A3	32.86	88.5%	87.10%	SELL OPTION	COVERED CALL	43.0	Dec 21	1	0.35		35	4,265	0.82%	0.25	17 Dec 2018	BUY	-0.10	0.59%	25.00		25.00	13d
-- 12 Dec 2018	SMPL	A1	19.40	32.6%	42.20%	SELL OPTION	CASH SECURED  PUT	20.0	Jan 18	2	0.85		170	3,830	4.44%	0.2	17 Jan 2019	BUY	-0.20	3.39%	130.00		130.00	36d
-- 13 Dec 2018	PLNT	A1	54.38	36.2%	57.40%	SELL OPTION	CASH SECURED  PUT	52.5	Jan 18	1	1.55		155	5,095	3.04%	0.22	9 Jan 2019	BUY	-0.22	2.61%	133.00		133.00	27d
-- 14 Dec 2018	CIEN	A1	34.32	38.1%	57.50%	SELL OPTION	CASH SECURED  PUT	34.0	Apr 18	2	2.49		498	6,302	7.90%	1.57	10 Jan 2019	BUY	-1.57	2.92%	184.00		184.00	27d
-- 17 Dec 2018	TWTR	A1	33.54	51.7%	50.70%	SELL OPTION	CASH SECURED  PUT	31.0	Mar 15	2	2.51		502	5,698	8.81%	2.26	25 Jan 2019	BUY	-1.90	2.14%	122.00		122.00	39d
-- 17 Dec 2018	HIIQ	A4	30.37	96.2%	121.40%	SELL OPTION	COVERED CALL	42.0	Feb 15	1	1.17		117	4,083	2.87%	0.5	12 Feb 2019	BUY	-0.50	1.64%	67.00		67.00	57d
-- 19 Dec 2018	TOL	A1	33.09	34.9%	51.60%	SELL OPTION	CASH SECURED  PUT	32.0	Mar 15	1	1.80		180	3,020	5.96%	0.8	10 Jan 2019	BUY	-0.80	3.31%	100.00		100.00	22d
-- 10 Jan 2019	CIEN	A1	36.89			BUY OPTION	PUT	37.0	Jan 18	1	-0.85		-85	3,785	-2.25%	0.01	18 Jan 2019	SELL	0.00	-2.25%	-85.00		-85.00	8d
-- 17 Jan 2019	NOMD	B1	17.86			SELL OPTION	CASH SECURED  PUT	17.5	Feb 15	3	0.40		120	5,130	2.34%	0.2	30 Jan 2019	BUY	-0.20	1.17%	60.00		60.00	13d
-- 22 Jan 2019	AMAT	A1	34.47			SELL OPTION	CASH SECURED  PUT	33.0	Feb 15	1	0.91		91	3,209	2.84%	0.38	23 Jan 2019	BUY	-0.38	1.65%	53.00		53.00	1d
-- 23 Jan 2019	EXEL	A1	22.71			SELL OPTION	CASH SECURED  PUT	22.0	Feb 15	1	1.05		105	2,095	5.01%	0.80	1 Feb 2019	BUY	-0.80	1.19%	25.00		25.00	9d
-- 24 Jan 2019	CVS	A1	65.75	26.6%	33.00%	SELL OPTION	CASH SECURED  PUT	64.0	Feb 15	1	0.85		85	6,315	1.35%	0.43	6 Feb 2019	BUY	-0.43	0.67%	42.00		42.00	13d
-- 29 Jan 2019	PLNT	A1	57.86	35%	35.00%	SELL OPTION	CASH SECURED  PUT	57.5	Mar 15	1	3.30		330	5,420	6.09%	2.72	12 Feb 2019	BUY	-2.78	0.96%	52.00		52.00	14d
-- 30 Jan 2019	AMAT	A1	38.83	37.5%	48.10%	SELL OPTION	CASH SECURED  PUT	38.0	Mar 15	1	1.79		179	3,621	4.94%	1.17	12 Feb 2019	BUY	-1.17	1.71%	62.00		62.00	13d
-- 1 Feb 2019	CIEN	A1	38.14			SELL OPTION	CASH SECURED  PUT	36.0	Mar 15	2	1.21		242	6,958	3.48%	0.83	13 Feb 2019	BUY	-0.84	1.06%	74.00		74.00	12d
-- 1 Feb 2019	BERY	A1	50.31	25%	33.80%	SELL OPTION	CASH SECURED  PUT	50.0	Mar 15	1	1.50		150	4,850	3.09%	0.75	15 Feb 2019	BUY	-0.75	1.55%	75.00		75.00	14d
-- 5 Feb 2019	CRUS	A1	37.71	35.6%	51.00%	SELL OPTION	CASH SECURED  PUT	35.0	Mar 15	1	0.75		75	3,425	2.19%	0.50	13 Feb 2019	BUY	-0.50	0.73%	25.00		25.00	8d
-- 5 Feb 2019	CSX	A1	67.05			SELL OPTION	CASH SECURED  PUT	65.0	Mar 15	1	1.13		113	6,387	1.77%	0.58	12 Feb 2019	BUY	-0.58	0.86%	55.00		55.00	7d
-- 6 Feb 2019	LEN	A1	46.86	29.8%	42.80%	SELL OPTION	CASH SECURED  PUT	45.0	Mar 15	1	1.00		100	4,400	2.27%	0.48	13 Feb 2019	BUY	-0.50	1.14%	50.00		50.00	7d
-- 7 Feb 2019	CVS	A1	66.06			SELL OPTION	CASH SECURED  PUT	65.0	Mar 15	1	1.71		171	6,329	2.70%	0.72	16 Feb 2019	BUY	-0.74	1.53%	97.00		97.00	9d
-- 7 Feb 2019	GPS	A1	24.76			SELL OPTION	CASH SECURED  PUT	24.0	Mar 15	1	1.10		110	2,290	4.80%	0.88	16 Feb 2019	BUY	-0.90	0.87%	20.00		20.00	9d
-- 12 Feb 2019	HIIQ	A5	38.37	97.3%	70.90%	SELL OPTION	COVERED CALL	42.0	Mar 15	1	3.00		300	3,900	7.69%	2.40	2 Mar 2019	BUY	-2.40	1.54%	60.00		60.00	18d
-- 15 Feb 2019	TWTR	A1	31.10	38.1%	47.90%	SELL OPTION	CASH SECURED  PUT	30.0	Mar 15	1	0.87		87	2,913	2.99%	0.33	26 Feb 2019	BUY	-0.35	1.79%	52.00		52.00	11d
-- 16 Feb 2019	WBA	A1	73.35	22.2%	19.90%	SELL OPTION	CASH SECURED  PUT	72.5	Mar 15	1	1.20		120	7,130	1.68%		6 Mar 2019	BUY ASSIGN	0.00	1.68%	120.00		120.00	18d
-- 16 Feb 2019	WGO	A1	31.68	48.4%	49.00%	SELL OPTION	CASH SECURED  PUT	35.0	Mar 15	1	3.70		370	3,130	11.82%	1.75	26 Feb 2019	BUY	-1.75	6.23%	195.00		195.00	10d
-- 20 Feb 2019	BMCH	A1	18.15	33.4%	39.70%	SELL OPTION	CASH SECURED  PUT	17.5	Apr 18	3	0.70		210	5,040	4.17%	0.30	1 Mar 2019	BUY	-0.35	2.08%	105.00		105.00	9d
-- 20 Feb 2019	CSX	A1	72.65	22.6%	24.60%	SELL OPTION	CASH SECURED  PUT	70.0	Apr 18	1	1.60		160	6,840	2.34%	1.29	6 Mar 2019	BUY	-1.29	0.45%	31.00		31.00	14d
-- 20 Feb 2019	VZ	A1	55.57	16.6%	19.80%	SELL OPTION	CASH SECURED  PUT	55.0	Apr 18	1	1.30		130	5,370	2.42%	0.78	26 Feb 2019	BUY	-0.78	0.97%	52.00		52.00	6d
-- 26 Feb 2019	EXEL	A1	22.37			SELL OPTION	CASH SECURED  PUT	22.0	Apr 18	3	1.30		390	6,210	6.28%	0.60	13 Mar 2019	BUY	-0.60	3.38%	210.00		210.00	15d
-- 27 Feb 2019	WFC	A1	49.67			SELL OPTION	CASH SECURED  PUT	50.0	Apr 18	1	1.52		152	4,848	3.14%	0.86	16 Mar 2019	BUY	-0.86	1.36%	66.00		66.00	17d
-- 1 Mar 2019	TWTR	A1	30.67			SELL OPTION	CASH SECURED  PUT	30.0	Apr 18	3	1.35		405	8,595	4.71%	0.84	13 Mar 2019	BUY	-0.84		153.00		153.00	12d
-- 1 Mar 2019	VZ	A1	57.11			SELL OPTION	CASH SECURED  PUT	55.0	Apr 18	1	0.67		67	5,433	1.23%	0.46	13 Mar 2019	BUY	-0.46	0.39%	21.00		21.00	12d
-- 1 Mar 2019	KO	A1	45.55			SELL OPTION	CASH SECURED  PUT	45.0	Apr 18	1	0.71		71	4,429	1.60%	0.64	6 Mar 2019	BUY	-0.64	0.16%	7.00		7.00	5d
-- 2 Mar 2019	APOG	A1	36.14			SELL OPTION	CASH SECURED  PUT	35.0	Apr 18	1	1.65		165	3,335	4.95%	1.47	15 Mar 2019	BUY	-1.50	0.45%	15.00		15.00	13d
-- 2 Mar 2019	HIIQ	A6	37.21			SELL OPTION	COVERED CALL	40.0	Apr 18	1	4.30		430	3,570	12.04%	2.70	13 Mar 2019	BUY	-2.85	4.06%	145.00		145.00	11d
-- 5 Mar 2019	TOL	A1	36.79			SELL OPTION	CASH SECURED  PUT	35.0	Apr 18	1	1.03		103	3,397	3.03%	0.67	19 Mar 2019	BUY	-0.68	1.03%	35.00		35.00	14d
-- 5 Mar 2019	KEM	A1	18.97			SELL OPTION	CASH SECURED  PUT	18.0	Apr 18	3	0.75		225	5,175	4.35%	0.55	04 Apr 2019	BUY	-0.55	1.16%	60.00	-0.17	60.17	30d
-- 5 Mar 2019	BCC	A1	27.96			SELL OPTION	CASH SECURED  PUT	27.5	Apr 18	2	1.00		200	5,300	3.77%	0.45	6 Apr 2019	BUY	-0.50	1.89%	100.00	3.29	96.71	32d
-- 6 Mar 2019	WBA	A2	63.63			SELL OPTION	COVERED CALL	72.5	Apr 18	1	0.20		20	7,230	0.28%	0.09	7 Mar 2019	BUY	-0.11	0.12%	9.00		9.00	1d
-- 7 Mar 2019	WBA	A3	61.37			SELL OPTION	COVERED CALL	70.0	Apr 18	1	0.23		23	6,977	0.33%	0.01	03 Apr 2019	BUY	-0.01	0.32%	22.00	0	22.00	27d
-- 8 Mar 2019	LEN	A1	47.60			SELL OPTION	CASH SECURED  PUT	45.0	Apr 18	1	1.21		121	4,379	2.76%	0.79	19 Mar 2019	BUY	-0.79	0.96%	42.00		42.00	11d
-- 8 Mar 2019	WGO	A1	31.18			SELL OPTION	CASH SECURED  PUT	30.0	Apr 18	2	1.70		340	5,660	6.01%	1.50	15 Mar 2019	BUY	-1.55	0.53%	30.00		30.00	7d
-- 8 Mar 2019	GNTX	A1	20.47			SELL OPTION	CASH SECURED  PUT	20.0	Apr 18	3	0.53		159	5,841	2.72%	0.30	16 Mar 2019	BUY	-0.35	0.92%	54.00		54.00	8d
-- 9 Mar 2019	AMAT	A1	37.64			SELL OPTION	CASH SECURED  PUT	37.0	Apr 18	2	1.31		262	7,138	3.67%	0.72	14 Mar 2019	BUY	-0.73	1.63%	116.00		116.00	5d
-- 12 Mar 2019	TDS	A1	31.96			SELL OPTION	CASH SECURED  PUT	30.0	Apr 18	1	0.50		50	2,950	1.69%	0.37	16 Mar 2019	BUY	-0.38	0.41%	12.00		12.00	4d
-- 12 Mar 2019	AEIS	A1	47.86			SELL OPTION	CASH SECURED  PUT	45.0	Apr 18	1	1.08		108	4,392	2.46%	0.70	19 Mar 2019	BUY	-0.72	0.82%	36.00		36.00	7d
-- 12 Mar 2019	HPQ	A1	19.03			SELL OPTION	CASH SECURED  PUT	18.0	Apr 18	3	0.21		63	5,337	1.18%	0.09	14 Mar 2019	BUY	-0.10	0.62%	33.00		33.00	2d
-- 12 Mar 2019	CVS	A1	53.80			SELL OPTION	CASH SECURED  PUT	52.5	Apr 18	1	1.41		141	5,109	2.76%	0.54	14 Mar 2019	BUY	-0.55	1.68%	86.00		86.00	2d
-- 13 Mar 2019	HIIQ	A7	38.52			SELL OPTION	COVERED CALL	44.0	May 17	1	3.26		326	4,074	8.00%	0.27	23 Apr 2019	BUY	-0.10	7.76%	316.00	0	316.00	41d
-- 14 Mar 2019	HPQ	A1	19.53			SELL OPTION	CASH SECURED  PUT	19.0	Apr 18	4	0.25		100	7,500	1.33%	0.13	19 Mar 2019	BUY	-0.14	0.59%	44.00	4.72	39.28	5d
-- 14 Mar 2019	HTZ	A1	16.67	53%		SELL OPTION	CASH SECURED  PUT	19.0	Mar 15	4	2.31		924	6,676	13.84%	2.31	16 Mar 2019	BUY	-2.31	0.00%	0.00	3.22	-3.22	2d
-- 14 Mar 2019	WLK	A1	69.30	29.5%		SELL OPTION	CASH SECURED  PUT	65.0	Apr 18	1	0.96		96	6,404	1.50%	0.95	16 Mar 2019	BUY	-0.95	0.02%	1.00	2.28	-1.28	2d
-- 15 Mar 2019	VZ	A1	57.88	13.77%		SELL OPTION	CASH SECURED  PUT	57.5	Apr 18	1	0.96		96	5,654	1.70%	0.47	23 Mar 2019	BUY	-0.50	0.81%	46.00		46.00	8d
-- 16 Mar 2019	AMAT	A1	40.50	27.98%		SELL OPTION	CASH SECURED  PUT	39.0	Apr 18	2	0.76		152	7,648	1.99%	0.46	22 Mar 2019	BUY	-0.45		62.00		62.00	6d
-- 16 Mar 2019	CSCO	A1	53.47	16.38%		SELL OPTION	CASH SECURED  PUT	52.0	Apr 5	1	0.36	0.79	36	5,164	0.70%	0.23	30 Mar 2019	BUY	-0.10	0.50%	26.00	1.09	24.91	14d
-- 16 Mar 2019	CRUS	A1	41.31			SELL OPTION	CASH SECURED  PUT	40.0	Apr 12	1	0.75	0.34	75	3,925	1.91%	0.25	22 Mar 2019	BUY	-0.29		46.00	1.94	44.06	6d
-- 16 Mar 2019	MOMO	A1	38.42			SELL OPTION	CASH SECURED  PUT	35.0	Apr 5	1	0.45	1.08	45	3,455	1.30%	0.35	20 Mar 2019	BUY	-0.35	0.29%	10.00	0.34	8.58	4d
-- 19 Mar 2019	H	A1	72.83	22.42%		SELL OPTION	CASH SECURED  PUT	70.0	Apr 18	1	0.65	1.94	65	6,935	0.94%	0.52	22 Mar 2019	BUY	-0.56	0.13%	9.00	0.29	6.77	3d
-- 19 Mar 2019	GNTX	A1	20.62	26.25%		SELL OPTION	CASH SECURED  PUT	20.0	Apr 18	3	0.34	2.37	102	5,898	1.73%	0.22	04 Apr 2019	BUY	-0.10	1.22%	72.00	2.38	67.25	16d
-- 19 Mar 2019	MGA	A1	49.35	25.34%		SELL OPTION	CASH SECURED  PUT	47.5	Apr 18	1	0.55		55	4,695	1.17%	0.15	03 Apr 2019	BUY	-0.19	0.77%	36.00	0.34	35.66	15d
-- 19 Mar 2019	CVS	A1	56.57	29.87%		SELL OPTION	CASH SECURED  PUT	55.0	Apr 12	1	0.85	0.79	85	5,415	1.57%	0.00	13 Apr 2019	BUY	0.00	1.57%	85.00		84.21	25d
-- 20 Mar 2019	IP	A1	45.36			SELL OPTION	CASH SECURED  PUT	45.5	Apr 5	1	0.53	0.79	53	4,497	1.18%	0.26	30 Mar 2019	BUY	-0.28	0.56%	25.00	0.79	24.21	10d
-- 20 Mar 2019	KO	A1	45.47			SELL OPTION	CASH SECURED  PUT	45.0	Apr 18	1	0.38	1.09	38	4,462	0.85%	0.24	26 Mar 2019	BUY	-0.26	0.27%	12.00	1.09	10.91	6d
-- 20 Mar 2019	ICHR	A1	22.55			SELL OPTION	CASH SECURED  PUT	20.0	Apr 18	2	0.30	1.58	60	3,940	1.52%	0.15	30 Mar 2019	BUY	-0.19	0.56%	22.00	-0.12	22.12	10d
-- 20 Mar 2019	LKQ	A1	28.78			SELL OPTION	CASH SECURED  PUT	27.5	Apr 18	2	0.25	0.08	50	5,450	0.92%	0.10	02 Apr 2019	BUY	-0.10	0.55%	30.00		29.92	13d
-- 21 Mar 2019	AEIS	A1	49.40	32.01%		SELL OPTION	CASH SECURED  PUT	45.0	Apr 18	1	0.44	0.29	44	4,456	0.99%	0.20	03 Apr 2019	BUY	-0.25	0.43%	19.00	1.09	17.62	13d
-- 22 Mar 2019	CYBR	A1	117.66	32.29%		SELL OPTION	CASH SECURED  PUT	112.0	Apr 12	1	1.60	0.29	160	11,040	1.45%	0.67	02 Apr 2019	BUY	-0.75	0.77%	85.00	-0.34	85.34	11d
-- 22 Mar 2019	KEYS	A1	87.53			SELL OPTION	CASH SECURED  PUT	82.5	Apr 18	1	0.41	0.24	41	8,209	0.50%	0.17	02 Apr 2019	BUY	-0.20	0.26%	21.00	-1.09	22.09	11d
-- 22 Mar 2019	WLK	A1	69.81			SELL OPTION	CASH SECURED  PUT	65.0	Apr 18	1	0.62	0.29	62	6,438	0.96%	0.30	03 Apr 2019	BUY	-0.35	0.42%	27.00	1.09	25.62	12d
-- 22 Mar 2019	QNST	A1	13.72			SELL OPTION	CASH SECURED  PUT	12.5	Apr 18	3	0.20	0.12	60	3,690	1.63%	0.15	6 Apr 2019	BUY	-0.10	0.81%	30.00	0.13	29.75	15d
-- 22 Mar 2019	FOXF	A1				SELL OPTION	CASH SECURED  PUT	65.0	Apr 18	1	0.59		59	6,441	0.92%	0.10	6 Apr 2019	BUY	-0.10	0.76%	49.00	0.29	48.71	15d
-- 23 Mar 2019	BERY	A1	53.68			SELL OPTION	CASH SECURED  PUT	52.5	Apr 18	1	0.70	1.94	70	5,180	1.35%	0.47	27 Mar 2019	BUY	-0.48	0.42%	22.00	1.09	20.91	4d
-- 23 Mar 2019	CRUS	A1	41.80			SELL OPTION	CASH SECURED  PUT	40.0	Apr 5	1	0.33	0.29	33	3,967	0.83%	0.20	27 Mar 2019	BUY	-0.24	0.23%	9.00	1.94	7.06	4d
-- 30 Mar 2019	AMAT	A1	39.71	29.72%		SELL OPTION	CASH SECURED  PUT	38.0	Apr 12	1	0.33	0.79	33	3,767	0.88%	0.11	02 Apr 2019	BUY	-0.11	0.58%	22.00	1.09	20.91	3d
-- 30 Mar 2019	PLNT	A1	68.78	37%		SELL OPTION	CASH SECURED  PUT	65.0	Apr 18	1	0.35	0.34	35	6,465	0.54%	0.12	03 Apr 2019	BUY	-0.15	0.31%	20.00	0.34	19.32	4d
-- 02 Apr 2019	APOG	A1	38.21			SELL OPTION	CASH SECURED  PUT	35.0	Apr 18	1	0.78	1.50	78	3,422	2.28%	0.65	04 Apr 2019	BUY	-0.65	0.38%	13.00	0.34	11.16	2d
-- 02 Apr 2019	AYI	A1	123.47			SELL OPTION	CASH SECURED  PUT	115.0	Apr 18	1	2.20	0.35	220	11,280	1.95%	0.10	04 Apr 2019	BUY	-0.10	1.86%	210.00		209.65	2d
-- 02 Apr 2019	EXEL	A1	24.35	46.5%		SELL OPTION	CASH SECURED  PUT	24.0	Apr 18	1	0.60	0.60	60	2,340	2.56%	0.45	04 Apr 2019	BUY	-0.48	0.51%	12.00	1.09	10.31	2d
-- 02 Apr 2019	NUE	A1	59.53			SELL OPTION	CASH SECURED  PUT	58.0	Apr 12	1	0.40	1.10	40	5,760	0.69%	0.18	5 Apr 2019	BUY	-0.19	0.36%	21.00	0.61	19.29	3d
-- 02 Apr 2019	AGX	A1	50.71	44.93%		SELL OPTION	CASH SECURED  PUT	50.0	Apr 18	1	1.65	1.50	165	4,835	3.41%	0.80	12 Apr 2019	BUY	-0.80	1.76%	85.00	1.49	82.01	10d
-- 03 Apr 2019	HIIQ	B1	25.55	115.7%		SELL OPTION	CASH SECURED  PUT	25.0	Apr 18	1	2.35	0.35	235	2,265	10.38%	1.95	04 Apr 2019	BUY	-1.95	1.77%	40.00	0.34	39.31	1d
-- 03 Apr 2019	WBA	B1	55.42	26.41%		SELL OPTION	CASH SECURED  PUT	55.0	Apr 12	1	0.85	1.58	85	5,415	1.57%	0.48	9 Apr 2019	BUY	-0.50	0.65%	35.00	1.09	32.33	6d
-- 03 Apr 2019	SMPL	A1	21.65	36%		SELL OPTION	CASH SECURED  PUT	20.0	Apr 18	4	0.25	0.22	100	7,900	1.27%	0.05	5 Apr 2019	BUY	-0.09	0.81%	64.00	2.38	61.40	2d
-- 04 Apr 2019	BERY	A1	54.78	23.371%		SELL OPTION	CASH SECURED  PUT	55.0	May 17	1	2.00	0.35	200	5,300	3.77%	1.60	6 Apr 2019	BUY	-1.60	0.75%	40.00	0.34	39.31	2d
-- 04 Apr 2019	LEN	A1	49.71	28.356%		SELL OPTION	CASH SECURED  PUT	50.0	May 17	1	2.03	0.80	203	4,797	4.23%	1.18	6 Apr 2019	BUY	-1.20	1.73%	83.00	0.79	81.41	2d
-- 05 Apr 2019	CSCO	A1	55.23	17.363%		SELL OPTION	COVERED CALL	54.5	Apr 12	4	0.92	2.99	368	21,432	1.72%	0.00	13 Apr 2019	BUY	0.00	1.72%	368.00		365.01	8d
-- 06 Apr 2019	VZ	A1	58.89			SELL OPTION	CASH SECURED  PUT	58.0	Apr 26	1	0.91	0.80	91	5,709	1.59%	0.62	13 Apr 2019	BUY	-0.63	0.49%	28.00	1.09	26.11	7d
-- 9 Apr 2019	BERY	A1	56.11	22.576%		SELL OPTION	CASH SECURED  PUT	55.0	May 17	2	1.35	0.10	270	10,730	2.52%	0.75	13 Apr 2019	BUY	-0.77	1.08%	116.00	-0.01	115.91	4d
-- 9 Apr 2019	AMAT	A1	42.87	31.811%		SELL OPTION	CASH SECURED  PUT	42.0	May 10	2	1.13	0.99	226	8,174	2.76%	0.74	17 Apr 2019	BUY	-0.77	0.88%	72.00	2.56	68.45	8d
-- 9 Apr 2019	SWKS	A1	87.42	34.37%		SELL OPTION	CASH SECURED  PUT	85.0	Apr 26	1	1.12	0.30	112	8,388	1.34%	0.75	13 Apr 2019	BUY	-0.60	0.62%	52.00		51.70	4d

-- https://www.uuidgenerator.net/version4
-- 704d4679-1a27-491f-aceb-0948810e4a4b
-- 296d35f2-9225-40e7-a38b-1003d9fc75ce
-- 8262ed64-367d-41ca-b688-fe49e2636196
-- 3e0705cf-6622-42c6-9f1e-48929cd0f629
-- 9e83930b-bb8e-4810-b836-1ecb059f6167
-- 9b89cd98-574a-423f-8a1b-29cc5563ead5
-- 212f1065-d596-47ba-bcb8-11480a0a25cf
-- bc100c7f-0858-4b2d-a308-a90d1fcf77d8
-- 6131f114-aadc-47bf-9167-acc004c0b177
-- 0fad99d2-9d43-4e52-b89e-749585cf356b
-- 26a224f4-bc08-4850-9125-3a0ee8152118
-- 0157c00c-3b36-47ed-a907-2e2b5615a561
-- 78c237ca-07be-4097-bd5d-bae9aa590ef9
-- 2a49d556-5a8e-43fe-bc87-b8d76e779267
-- 1dffc97d-0dfa-432a-993e-0deedbea1095
-- 6d5d6e2c-4df2-4d05-bfe1-1370a410bdee