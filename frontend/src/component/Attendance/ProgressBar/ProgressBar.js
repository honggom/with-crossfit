import { Progress } from 'semantic-ui-react'
import './ProgressBar.css';
import styles from './ProgressBar.module.css';
import React, { useState } from 'react';

export default function ProgressBar({ totalDay, totalAttendanceDay }) {

    return (
        <>
            <Progress color='blue' progress='percent' value={totalAttendanceDay} total={totalDay} active className={styles.progressBar}/>
        </>
    );
}