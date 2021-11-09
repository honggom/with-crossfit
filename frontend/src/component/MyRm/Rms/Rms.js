import { calculateToKg } from '../../../util/util';

export default function Rms({rms}) {
    return (
        <>
            {rms.map(rm => (
                <tr key={rm.id}>
                    <td>{rm.name}</td>
                    <td>{rm.repetition}</td>
                    <td>{rm.lb + "/" + calculateToKg(rm.lb)}</td>
                </tr>
            ))}
        </>
    );
}