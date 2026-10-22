param([string]$Image = 'calculator:v1')
$ErrorActionPreference = 'Stop'

Write-Output "docker run --rm $Image"
docker run --rm $Image
if ($LASTEXITCODE -ne 0) { throw 'Default demo failed.' }

$cases = @(
    @{ Left='12'; Op='+'; Right='3'; Expected='12 + 3 = 15.0' },
    @{ Left='12'; Op='-'; Right='3'; Expected='12 - 3 = 9.0' },
    @{ Left='12'; Op='*'; Right='3'; Expected='12 * 3 = 36.0' },
    @{ Left='12'; Op='/'; Right='3'; Expected='12 / 3 = 4.0' },
    @{ Left='-3'; Op='*'; Right='4'; Expected='-3 * 4 = -12.0' },
    @{ Left='5'; Op='/'; Right='2'; Expected='5 / 2 = 2.5' }
)
foreach ($case in $cases) {
    $actual = docker run --rm $Image $case.Left $case.Op $case.Right
    if ($LASTEXITCODE -ne 0 -or $actual -cne $case.Expected) {
        throw "Unexpected calculator result: $actual"
    }
    Write-Output "PASS: $actual"
}

# A nonzero exit is expected for division by zero; capture stderr as evidence.
$ErrorActionPreference = 'Continue'
$failure = docker run --rm $Image 9 / 0 2>&1
$status = $LASTEXITCODE
$ErrorActionPreference = 'Stop'
if ($status -ne 1 -or ($failure -join "`n") -notmatch 'Division by zero is not allowed') {
    throw "Unexpected error behavior (exit $status): $failure"
}
Write-Output "PASS: division by zero returned exit $status and the expected error."
Write-Output 'All container checks passed.'
