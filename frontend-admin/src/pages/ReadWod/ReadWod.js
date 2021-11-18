import { useParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import { getWodById } from '../../api/pages/ReadWod';
import styles from './ReadWod.module.css';
import DatePicker from 'react-datepicker';
import TextReader from '../../component/TextReader';
import { useNavigate } from 'react-router-dom';
import { errorHandle } from '../../util/util';

export default function ReadWod() {

  let navigate = useNavigate();
  let params = useParams();
  const [wod, setWod] = useState(null);

  useEffect(() => {
    getWodById(params.wodId).then((response) => {
      setWod(response.data);
    }).catch((error) => {
      errorHandle(error, navigate);
    });
  }, []);

  if (wod !== undefined && wod !== null) {
    return (
      <div className={styles.wrapper}>

        <div className={styles.topWrapper}>
          <p>{wod.title}</p>
        </div>

        <div className={styles.middleWrapper}>
          <span>WOD 일시</span>
          <DatePicker className={styles.border} selected={new Date(wod.date)} readOnly />
        </div>

        <div className={styles.middleWrapper}>
          <span>작성 일시</span>
          <DatePicker className={styles.border} selected={new Date(wod.createdAt)} readOnly />
        </div>

        <div className={styles.middleWrapper}>

        </div>

        <div className={styles.middleWrapper}>
          <span>본문</span>
          <TextReader text={wod.content} />
        </div>

        <div className={styles.bottomWrapper}>
        </div>

      </div>
    );
  } else {
    return null;
  }
}
